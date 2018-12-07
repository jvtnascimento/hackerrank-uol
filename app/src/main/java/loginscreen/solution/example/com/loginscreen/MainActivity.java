package loginscreen.solution.example.com.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.regex.Pattern;

import loginscreen.solution.example.com.loginscreen.models.AuthCredentials;

public class MainActivity extends AppCompatActivity {

    //menu
    private Button menuSignInButton;
    private Button menuSignUpButton;

    //buttons
    private Button signInButton;
    private Button signUpButton;

    //signup
    private LinearLayout nameLayout;
    private EditText name;
    private EditText phone;

    //login
    private ViewFlipper viewFlipper;
    private LinearLayout commonLayout;
    private EditText email;
    private EditText password;

    AuthCredentials credentials = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        this.initComponents();
        this.configureActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initComponents() {
        //menu
        this.menuSignInButton = (Button) findViewById(R.id.bt_login);
        this.menuSignUpButton = (Button) findViewById(R.id.bt_signup);

        //buttons
        this.signInButton = (Button) findViewById(R.id.bt_sign_in);
        this.signUpButton = (Button) findViewById(R.id.bt_create);

        //common
        this.viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        this.commonLayout = (LinearLayout) findViewById(R.id.lt_common);
        this.email = (EditText) findViewById(R.id.et_email);
        this.password = (EditText) findViewById(R.id.et_password);

        //signup
        this.nameLayout = (LinearLayout) findViewById(R.id.lt_name);
        this.name = (EditText) findViewById(R.id.et_name);
        this.phone = (EditText) findViewById(R.id.et_phone);
    }

    private void configureSignInView() {
        this.nameLayout.setVisibility(View.INVISIBLE);
        this.viewFlipper.showPrevious();
    }

    private void configureSignUpView() {
        this.nameLayout.setVisibility(View.VISIBLE);
        this.viewFlipper.showNext();
    }

    private void onSignInButtonTap() {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        if (!email.equals("") && !password.equals("")) {

            if (this.credentials == null || this.credentials.getEmail().equals("") || this.credentials.getPassword().equals("")) {
                this.showMessage("É necessário cadastrar o usuário antes de fazer o Sign In.");
            }
            else if (email.equals(this.credentials.getEmail()) && password.equals(this.credentials.getPassword())){
                startLoginWelcomeActivity();
            }
            else {
                this.showMessage("Informações de email e/ou senha incorretos.");
            }
        } else {
            this.showMessage("É necessário informar todos os campos.");
        }
    }

    private void onSignUpButtonTap() {
        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String phone = this.phone.getText().toString();

        if (name.equals("") || email.equals("") || password.equals("") || phone.equals("")) {
            this.showMessage("É necessário informar todos os campos.");
        }
        else if (!isValidEmail(email)) {
            this.showMessage("É necessário informar um email válido");
        }
        else if (!isStrogPassword(password)) {
            this.showMessage("É necessário informar uma senha com no minimo: " +
                    "Uma letra maíuscula; " +
                    "Uma letra minúscula; " +
                    "Um número; " +
                    "Um caracter especial;");
        }
        else if (!isValidPhone(phone)) {
            this.showMessage("O telefone precisa ter 10 números!");
        }
        else {
            this.credentials = new AuthCredentials();
            this.credentials.setName(name);
            this.credentials.setEmail(email);
            this.credentials.setPassword(password);
            this.credentials.setPhone(phone);

            this.showMessage("Usuário cadastrado com sucesso! Faça o Log In.");
        }
    }

    private void startLoginWelcomeActivity() {
        Intent intent = new Intent(this, LoginWelcomeActivity.class);
        intent.putExtra("credentials", this.credentials);
        startActivity(intent);
        finish();
    }

    private void showMessage(String message){
        Toast.makeText(this, message ,Toast.LENGTH_LONG).show();
    }

    private void configureActivity() {

        this.menuSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureSignInView();
            }
        });

        this.menuSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureSignUpView();
            }
        });

        this.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignInButtonTap();
            }
        });

        this.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUpButtonTap();
            }
        });
    }

    private boolean isStrogPassword(String password) {
        if (password.length() < 6)
            return false;

        boolean hasNumber = false;
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (c >= '0' && c <= '9')
                hasNumber = true;
            else if (c >= 'A' && c <= 'Z')
                hasUppercase = true;
            else if (c >= 'a' && c <= 'z')
                hasLowercase = true;
            else
                hasSpecial = true;
        }

        return hasNumber && hasUppercase && hasLowercase && hasSpecial;
    }

    public static boolean isValidEmail(String email){
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        +"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        if (phone.length() != 10)
            return false;
        else
            return true;
    }
}

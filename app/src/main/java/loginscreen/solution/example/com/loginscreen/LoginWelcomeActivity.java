package loginscreen.solution.example.com.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import loginscreen.solution.example.com.loginscreen.models.AuthCredentials;

public class LoginWelcomeActivity extends AppCompatActivity {

    //components
    private TextView name;
    private TextView email;
    private TextView phone;

    private AuthCredentials credentials = null;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(LoginWelcomeActivity.this, MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_welcome);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.credentials = (AuthCredentials) extras.getSerializable("credentials");
        }

        this.initComponents();
        this.configureActivity();
    }

    private void initComponents() {
        this.name = (TextView) findViewById(R.id.tv_name);
        this.email = (TextView) findViewById(R.id.tv_email);
        this.phone = (TextView) findViewById(R.id.tv_phone);
    }

    private void configureActivity() {
        if (this.credentials != null) {
            this.name.setText("Nome: " + this.credentials.getName());
            this.email.setText("Email: " + this.credentials.getEmail());
            this.phone.setText("Telefone: " + this.credentials.getPhone());
        }
    }
}

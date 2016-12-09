package aghilas.ise.fr.vision_phantom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import dji.sdk.api.*;
import dji.sdk.api.DJIDroneTypeDef;
import dji.sdk.api.DJIError;
import dji.sdk.interfaces.DJIDroneTypeChangedCallback;
import dji.sdk.interfaces.DJIGeneralListener;

public class MainActivity extends AppCompatActivity {
    private final int SHOWDIALOG = 2;
    private static final String TAG = "MainActivity";

    private Button cam_btn, info_btn, baterie_btn, apropos_btn;


    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SHOWDIALOG:
                    showMessage(getString(R.string.activation_message_title),(String)msg.obj);
                    break;

                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cam_btn= (Button) findViewById(R.id.camera_btn);
        baterie_btn=(Button) findViewById(R.id.baterie_btn);
        info_btn=(Button) findViewById(R.id.infos_btn);
        apropos_btn=(Button) findViewById(R.id.apropos_btn);

        new Thread(){
            public void run() {
                try {
                    DJIDrone.checkPermission(getApplicationContext(), new DJIGeneralListener() {

                        @Override
                        public void onGetPermissionResult(int result) {
                            // TODO Auto-generated method stub
                            Log.e(TAG, "onGetPermissionResult = "+result);
                            Log.e(TAG, "onGetPermissionResultDescription = "+ DJIError.getCheckPermissionErrorDescription(result));
                            if (result == 0) {
                                handler.sendMessage(handler.obtainMessage(SHOWDIALOG, "Check Permission Success"));
                                Toast.makeText(getApplicationContext(), DJIError.getCheckPermissionErrorDescription(result),Toast.LENGTH_LONG).show();
                            } else {
                                handler.sendMessage(handler.obtainMessage(SHOWDIALOG, "Activation échoué, vérifiez votre clé ou votre connexion"+"\n"+getString(R.string.activation_error_code)+result));
                                Toast.makeText(getApplicationContext(), getString(R.string.activation_error)+DJIError.getCheckPermissionErrorDescription(result)+"\n"+getString(R.string.activation_error_code)+result,Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();




        cam_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        baterie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(i);

            }
        });

        apropos_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }


    public void showMessage(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

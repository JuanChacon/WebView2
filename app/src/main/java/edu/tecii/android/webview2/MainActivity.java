package edu.tecii.android.webview2;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
     Button btnGo;
    Button btnBa;
    Button btnFo;
    EditText te;
     WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = (WebView) this.findViewById(R.id.wV);
        btnGo=(Button)findViewById(R.id.buttonGo);
        btnBa=(Button)findViewById(R.id.back);
        btnFo=(Button)findViewById(R.id.forward);
        te = (EditText)findViewById(R.id.editText);

        myWebView.setWebViewClient(new WebViewClient()); //evita que los enlaces se abran fuera de la app en el navegador de android
        myWebView.setWebViewClient(new WebViewClient(){
            @Override //metodo para poner visibles los botones segun el caso
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if(myWebView.canGoBack()){
                    btnBa.setEnabled(true);
                }else{btnBa.setEnabled(false);}
                //btnBa.setEnabled(false);

                if(myWebView.canGoForward()){
                    btnFo.setEnabled(true);
                }else{btnFo.setEnabled(false);}
                //btnFo.setEnabled(false);

            }

            @Override  // se crea una alerta cuando no se encuentra el dominio ingresado
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //super.onReceivedError(view, errorCode, description, failingUrl);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("URL no encontrada" + "\n"+ description).setPositiveButton("Aceptar", null).setTitle("onReceivedError");
                builder.show();
            }
        });
//boton para abrir la pagina en la webview
    btnGo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
       String cade = te.getText().toString();
        if(cade.equals("")) {
           errorCapturaDir();
        }else {
            myWebView.loadUrl("http://www." + te.getText().toString() + ".com");
            Toast.makeText(getApplicationContext(), "http://www." + te.getText().toString() + ".com", Toast.LENGTH_SHORT).show();
            WebSettings settings = myWebView.getSettings();
            settings.setAppCacheEnabled(true);
            settings.setJavaScriptEnabled(true);
            settings.setAllowContentAccess(true);
            settings.setBuiltInZoomControls(true);

        }
        }
    });
    //boton para ir para atras de las paginas visitadas
     btnBa.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
                 myWebView.goBack();
         }
     });
    //Boton para ir para adelante en las paginas visitadas
     btnFo.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
                 myWebView.goForward();

         }
     });

    }
    //metodo para crear una alerta cuando el usuario no escribio algo en el edittext
    public void errorCapturaDir(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("URL no encontrada").setPositiveButton("Aceptar", null).setTitle("Error al cargar URL");
        builder.show();
    }




}

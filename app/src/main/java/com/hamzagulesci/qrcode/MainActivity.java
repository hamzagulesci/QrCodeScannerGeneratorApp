package com.hamzagulesci.qrcode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hanzoapps.qrkod.R;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    // EditText: Input field where the user enters the text for generating a QR code.
    // EditText: Kullanıcının QR kodu üretmek için metin gireceği alan.
    EditText edit_input;

    // Button: Button to trigger QR code generation.
    // Button: QR kod oluşturma işlemini başlatan buton.
    Button bt_generate;

    // Button: Button to trigger the QR code scanning process.
    // Button: QR kod tarama işlemini başlatan buton.
    Button btn_scan;

    // ImageView: Displays the generated QR code.
    // ImageView: Oluşturulan QR kodun gösterileceği alan.
    ImageView iv_qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Finds the input field for entering the text.
        // Metin girişi için kullanılan EditText alanını bulur.
        edit_input = findViewById(R.id.edit_input);

        // Finds the ImageView where the QR code will be displayed.
        // QR kodunun gösterileceği ImageView'i bulur.
        iv_qr = findViewById(R.id.iv_qr);

        // Finds the button for generating the QR code and sets its click listener.
        // QR kod oluşturma butonunu bulur ve tıklama dinleyicisini ayarlar.
        bt_generate = findViewById(R.id.bt_generate);
        bt_generate.setOnClickListener(v->{

            // Gets the entered text.
            // Girilen Metni Alıyoruz
            String text = edit_input.getText().toString().trim();

            if (text.isEmpty()) {
                // If the text is empty, shows a warning message to the user.
                // Eğer metin boşsa, kullanıcıya bir uyarı mesajı gösterir.
                Toast.makeText(MainActivity.this, "Please enter a text, Lütfen bir metin girin!", Toast.LENGTH_SHORT).show();
            } else {
                // If the text is not empty, generates a QR code.
                // Metin boş değilse, QR kodu oluşturur.
                generateQr();
            }
        });

        // Finds the button for scanning QR codes and sets its click listener.
        // QR kod tarama butonunu bulur ve tıklama dinleyicisini ayarlar.
        btn_scan = findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(v->{
            scanCode();
        });
    }

    private void generateQr() {
        // Retrieves the entered text to generate the QR code.
        // QR kodu oluşturmak için girilen metni alır.
        String text = edit_input.getText().toString().trim();

        // Uses the ZXing library to encode the text into a QR code.
        // ZXing kütüphanesini kullanarak metni QR koduna dönüştürür.
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            // Generates a 600x600 pixel QR code matrix.
            // 600x600 piksel bir QR kod matrisi oluşturur.
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE,600,600);

            // Converts the matrix into a bitmap image.
            // Matrisi bir bitmap resmine dönüştürür.
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);

            // Displays the generated QR code in the ImageView.
            // Oluşturulan QR kodunu ImageView'e yerleştirir.
            iv_qr.setImageBitmap(bitmap); // Çıkan QR Kodu İmage View'e yerleştiriyoruz

        } catch (WriterException e) {
            // Logs any error that occurs during QR code generation.
            // QR kod oluşturma sırasında oluşan hataları kaydeder.
            e.printStackTrace();
        }
    }

    private void scanCode() {
        // Configures options for scanning QR codes.
        // QR kodlarını taramak için seçenekleri yapılandırır.
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up button turns on the flashlight, Ses açma flaşı açar!");

        // Enables sound after a successful scan.
        // Başarılı bir taramadan sonra ses çalmasını etkinleştirir.
        options.setBeepEnabled(true);

        // Locks the orientation during scanning.
        // Tarama sırasında ekran yönünü sabitler.
        options.setOrientationLocked(true);

        // Sets the custom capture activity.
        // Özel yakalama aktivitesini ayarlar.
        options.setCaptureActivity(CaptureAct.class);

        // Launches the scanning process.
        // Tarama işlemini başlatır.
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(),result->{
        if (result.getContents() !=null){

            // Retrieves the scanned QR code content.
            // Taratılan QR kodun içeriğini alır.
            String scannedContent = result.getContents();

            // Shows the scanned content in a dialog box.
            // Taranan içeriği bir diyalog kutusunda gösterir.
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Result, Sonuc");
            builder.setMessage(scannedContent);

            // Adds a copy button to copy the scanned content to the clipboard.
            // Taratılan içeriği panoya kopyalamak için bir kopyala butonu ekler.
            builder.setPositiveButton("Copy ,Kopyala", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Copying text to the clipboard using the ClipboardManager class
                    // ClipboardManager sınıfını kullanarak metni panoya kopyalama
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("QR Code Content", scannedContent);
                    clipboard.setPrimaryClip(clip);

                    // Displays a confirmation message.
                    // Bir onay mesajı gösterir.
                    Toast.makeText(MainActivity.this, "Copied, Metin panoya kopyalandı!", Toast.LENGTH_SHORT).show();
                }
            });

            // Adds an OK button to close the dialog.
            // Diyalog kutusunu kapatmak için bir OK butonu ekler.
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    });
}
package com.hamzagulesci.qrcode;

import android.os.Bundle;

import com.journeyapps.barcodescanner.CaptureActivity;

/**
 * ENGLISH:
 * This class extends the default CaptureActivity from the ZXing library.
 * By default, CaptureActivity handles QR code scanning efficiently.
 * If customization is needed (like modifying camera behavior, UI changes, or adding event listeners),
 * this class can be overridden and modified accordingly.
 *
 * Below are examples of possible customizations:
 * 1. Customize the camera settings (e.g., enable/disable autofocus or flash).
 * 2. Add custom UI elements, such as a logo or a "Close" button.
 * 3. Handle lifecycle events for the scanning activity.
 *
 * For now, this class is left empty because the default behavior provided
 * by CaptureActivity meets our current requirements.
 *
 * TURKISH:
 * Bu sınıf, ZXing kütüphanesinin varsayılan CaptureActivity sınıfını genişletir.
 * Varsayılan olarak, CaptureActivity QR kod tarama işlemini verimli bir şekilde gerçekleştirir.
 * Ancak, eğer özel bir kamera davranışı, UI değişiklikleri veya olay dinleyicileri eklemek isterseniz,
 * bu sınıfı özelleştirebilir ve gerekli değişiklikleri yapabilirsiniz.
 *
 * Aşağıda yapılabilecek bazı özelleştirmelerin örnekleri bulunmaktadır:
 * 1. Kamera ayarlarını özelleştirme (örneğin, odaklama veya flaş açma/kapama).
 * 2. Özel UI elemanları ekleme, örneğin bir logo veya "Kapat" butonu ekleme.
 * 3. Tarama işlemi sırasında gerçekleşen yaşam döngüsü olaylarını yönetme.
 *
 * Şu anda bu sınıf boş bırakılmıştır çünkü CaptureActivity'nin varsayılan işlevselliği
 * mevcut ihtiyaçlarımıza yeterli olmaktadır.
 */
public class CaptureAct extends CaptureActivity {
    /**
     * ENGLISH:
     * Override this method for custom initialization or settings if needed.
     *
     * TURKISH:
     * Gerekirse özel başlatma veya ayarlamalar için bu metodu özelleştirebilirsiniz.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

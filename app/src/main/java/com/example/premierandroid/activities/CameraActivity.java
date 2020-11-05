package com.example.premierandroid.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.premierandroid.R;
import com.scandit.datacapture.barcode.capture.BarcodeCapture;
import com.scandit.datacapture.barcode.capture.BarcodeCaptureListener;
import com.scandit.datacapture.barcode.capture.BarcodeCaptureSession;
import com.scandit.datacapture.barcode.capture.BarcodeCaptureSettings;
import com.scandit.datacapture.barcode.data.Barcode;
import com.scandit.datacapture.barcode.data.Symbology;
import com.scandit.datacapture.barcode.ui.overlay.BarcodeCaptureOverlay;
import com.scandit.datacapture.core.capture.DataCaptureContext;
import com.scandit.datacapture.core.data.FrameData;
import com.scandit.datacapture.core.source.Camera;
import com.scandit.datacapture.core.source.CameraSettings;
import com.scandit.datacapture.core.source.FrameSourceState;
import com.scandit.datacapture.core.ui.DataCaptureView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CameraActivity extends AppCompatActivity {

    public static final String SCANDIT_LICENSE_KEY = "AWIP1gKjHXrvQf/IxxpY6jQ3xOlxLbHSbQPvEPEMRt0vRLesf2cqNEdx09qvcN1931N+ZHNoSLCabD4rm3OabTk3lpZ2bEE+Kweyw7l7ePjybznW2k21MPtffz/JamLPIz9XiJsty5JyJeKGUCjxGgx279sPNc3rdA+yNCMVXPr63nfHxxHDVIPoraHuSrx0qu7Xx2HYIVegFc/pmGv5VfsGK5L88+zJZSAsP7xkho5EMS4pgORo+SWtHjU4WXuM57ldt9A5nAEUcGJGTo/V6vPQyo0MmYxyD3Dup19ihOuZJudt9VmOFd6VZa7ehiUkH7ZE4cR4GzAqre8SF6KxXGmPGcjJcsUjEop+r6J0G11qOxv+cv4Ds8cjIrdW34BweiR1dziRfNPTu3Cq/xPb8JY9Qe8TfxvDSvkcnrNPLPgWHHBOvUfWHfjZaoYWdFofzsjN/M/J992Xu/hZmFreyowYeUGMvVgjGQ/Qr5yNu7CUp4EtWItzk/UhNY6vq1+PQnsYF7VdN7bLlo8sfGTOn1JpXgEzspXZLSj4XQzkJYZ4BpbHdxyaItgLZt+TFcF91ufcm6U3kZ7RLsV0xeXoBO74ZymwLW+MFJ1SoF+09SCidPj5HXSsZAJheXE8Lx7cYTa4AN2o+VglGgNP5za6eyYCyhTQXHLP3fKaY4ywU8a4X0tnZGj0lUhu2ixxvbqBbWaOIDGHG1r/rHinWfXb3ydOqVVCmFx4wC/vRe1tt4TdA003NduV6DPsGHO8wIqtYCtnRwqr/x5+2i4W+dcuN2pH5gYm8ZqMHcCl5HC4baCGmUtIORueIqw4q3bUpQ2vow==";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        //scandit

        //mise en place de la liscence
        final DataCaptureContext dataCaptureContext = DataCaptureContext.forLicenseKey(SCANDIT_LICENSE_KEY);

        //mise en place des codes lisibles
        BarcodeCaptureSettings settings = new BarcodeCaptureSettings();
        settings.enableSymbology(Symbology.CODE128, true);
        settings.enableSymbology(Symbology.CODE39, true);
        settings.enableSymbology(Symbology.QR, true);
        settings.enableSymbology(Symbology.EAN8, true);
        settings.enableSymbology(Symbology.UPCE, true);
        settings.enableSymbology(Symbology.EAN13_UPCA, true);
        settings.enableSymbology(Symbology.DATA_MATRIX, true);

        final BarcodeCapture barcodeCapture = BarcodeCapture.forDataCaptureContext(dataCaptureContext, settings);

        //mise en place du listener
        BarcodeCaptureListener barcodeCaptureListener = new BarcodeCaptureListener() {
            @Override
            public void onBarcodeScanned(@NonNull BarcodeCapture barcodeCapture,
                                         @NonNull BarcodeCaptureSession session, @NonNull FrameData frameData) {
                List<Barcode> recognizedBarcodes = session.getNewlyRecognizedBarcodes();
                // Do something with the barcodes.

                System.out.println(recognizedBarcodes.get(0).getData());
            }

            @Override
            public void onSessionUpdated(@NotNull BarcodeCapture barcodeCapture, @NotNull BarcodeCaptureSession barcodeCaptureSession, @NotNull FrameData frameData) {

            }

            @Override
            public void onObservationStarted(@NotNull BarcodeCapture barcodeCapture) {

            }

            @Override
            public void onObservationStopped(@NotNull BarcodeCapture barcodeCapture) {

            }
        };

        barcodeCapture.addListener(barcodeCaptureListener);

        //permission camera

        CameraSettings cameraSettings = BarcodeCapture.createRecommendedCameraSettings();

        // Depending on the use case further camera settings adjustments can be made here.

        Camera camera = Camera.getDefaultCamera();

        if (camera != null) {
            camera.applySettings(cameraSettings);
        }

        dataCaptureContext.setFrameSource(camera);

        if (camera != null) {
            camera.switchToDesiredState(FrameSourceState.ON);
        }



        //permet d'avoir le retour camera (je crois)

        DataCaptureView dataCaptureView = DataCaptureView.newInstance(this, dataCaptureContext);
        setContentView(dataCaptureView);


        BarcodeCaptureOverlay overlay = BarcodeCaptureOverlay.newInstance(barcodeCapture, dataCaptureView);


    }
}
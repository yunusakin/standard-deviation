import java.util.ArrayList;
import java.util.List;

public class StandartSapmaHesaplayici {
    public double standartSapmaHesapla(short[] sayilar, short threadSayisi) throws InterruptedException {

        if (threadSayisi < 1 || threadSayisi > 20) {
            throw new IllegalArgumentException("threadSayisi 1 ile 20 arasında olmalıdır.");
        }

        List<Thread> threadList = new ArrayList<>();
        List<ToplamHesaplayici> hesaplayiciList = new ArrayList<>();

        // Veri setinin aritmetik ortalaması hesaplanır
        double aritmetikOrtalama = 0;
        for (short sayi : sayilar) {
            aritmetikOrtalama += sayi;
        }
        aritmetikOrtalama /= sayilar.length;

        // Threadlerin işlenecek aralıkları belirlenir
        int elemanSayisi = sayilar.length;
        int aralik = elemanSayisi / threadSayisi;
        int kalan = elemanSayisi % threadSayisi;

        // Threadler oluşturulur ve başlatılır
        int startIndex = 0;
        for (int i = 0; i < threadSayisi; i++) {
            int threadElemanSayisi = aralik + (i < kalan ? 1 : 0);
            short[] threadSayilar = new short[threadElemanSayisi];
            System.arraycopy(sayilar, startIndex, threadSayilar, 0, threadElemanSayisi);

            ToplamHesaplayici hesaplayici = new ToplamHesaplayici(threadSayilar, aritmetikOrtalama);
            Thread thread = new Thread(hesaplayici);
            threadList.add(thread);
            hesaplayiciList.add(hesaplayici);
            thread.start();

            startIndex += threadElemanSayisi;
        }

        // Threadlerin tamamlanması beklenir
        for (Thread thread : threadList) {
            thread.join();
        }

        // Threadlerden gelen değerler toplanır
        double karelerToplami = 0.0;
        for (ToplamHesaplayici hesaplayici : hesaplayiciList) {
            karelerToplami += hesaplayici.getKarelerToplami();
        }

        // Varyans hesaplanır
        double varyans = karelerToplami / (sayilar.length - 1);

        // Standard sapma hesaplanır
        double standardSapma = Math.sqrt(varyans);

        return standardSapma;
    }

    private class ToplamHesaplayici implements Runnable {
        private short[] sayilar;
        private double aritmetikOrtalama;
        private double karelerToplami;

        public ToplamHesaplayici(short[] sayilar, double aritmetikOrtalama) {
            this.sayilar = sayilar;
            this.aritmetikOrtalama = aritmetikOrtalama;
        }

        @Override
        public void run() {
            karelerToplami = 0.0;

            for (short sayi : sayilar) {
                double fark = sayi - aritmetikOrtalama;
                karelerToplami += fark * fark;
            }
        }

        public double getKarelerToplami() {
            return karelerToplami;
        }
    }

    public static void main(String[] args) {
        short[] sayilar = {4,5,6,12,34,54};
        short threadSayisi = 5;

        try {
            StandartSapmaHesaplayici hesaplayici = new StandartSapmaHesaplayici();
            double standardSapma = hesaplayici.standartSapmaHesapla(sayilar, threadSayisi);
            System.out.println("Standard Sapma: " + standardSapma);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

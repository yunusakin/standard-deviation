# Standart Sapma Hesaplama Projesi

Bu proje, bir veri setinin standart sapmasını hesaplamak için Java programlama dilinde paralel hesaplama yöntemlerini kullanır.

## Özellikler

- Veri setindeki sayıların aritmetik ortalamasını hesaplar.
- Veri setindeki varyansı hesaplar.
- Standart sapmayı hesaplar.

## Kullanım

1. `sayilar` dizisini istediğiniz veri setiyle güncelleyin.
2. `threadSayisi` değişkenini istediğiniz thread sayısıyla güncelleyin.
3. Projeyi çalıştırın.

```java
public class Main {
    public static void main(String[] args) {
        short[] sayilar = {10, 8, 10, 8, 8, 4};
        short threadSayisi = 3;

        try {
            StandartSapmaHesaplayici hesaplayici = new StandartSapmaHesaplayici();
            double standardSapma = hesaplayici.standartSapmaHesapla(sayilar, threadSayisi);
            System.out.println("Standard Sapma: " + standardSapma);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

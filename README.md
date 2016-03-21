# Omubumu

Basit bir anket özelliğini, iki konuyu resimlerle karşılaştırmak ve oylama sistemi sağlayan bir sosyal platform startup projesidir. Veritabanı tipi MySQL, Backend PHP REST API ile yazıldı. Client tarafta Android, Windows 8.1 ve Windows Phone 8.1 mevcuttur.

# Nasıl Kurulur

Database Schema içerisinde bulunan SQL Script'i MySQL veritabanı üzerine kurmanız gerekir.

Kurulan veritabanı bağlantı bilgilerini OmuBumuAPI içerisinde bulunan PHP REST API içerisinde Core/Config.php dosyası içerisinde ilgili yerlere girmeniz gerekir.

Ardından PHP REST API yı bir host edebileceğiniz server/hosting e aktarmanız gerekir. Endpoint noktası olarak ise host ettiğiniz adresi kullanacaksınız.

Endpoint adresinizi Android uygulaması için Android klasöründe /app/src/main/java/com/hsd/omubumu/Common/DataClient.java dosyası içerisinde Uri olarak değiştiriniz.

Shared Windows 8.1 & Windows Phone 8.1 uygulaması için ise OmuBumuUA klasörü içerisinde OmuBumu/OmuBumu.Shared/Common/DataClient.cs dosyası içerisinde 30. satırda bulunan endpoint adresi ile değiştirin.

#Geliştiriciler

Kadircan Kırkoyun

Oğuzhan Akıncı

Barış Ceviz

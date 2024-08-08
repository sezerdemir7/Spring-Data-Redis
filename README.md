# Redis Doğrulama Kodu Servisi

Bu repo, Redis kullanarak basit bir doğrulama kodu servisini uygulayan bir Spring Boot uygulaması içerir. Servis, rastgele bir doğrulama kodu üretir, bu kodu Redis'te belirli bir süreliğine saklar ve kullanıcı giriş yapmaya çalıştığında kodu doğrular.

## Proje Yapısı

- **`config/`**: Redis için yapılandırma sınıflarını içerir.
    - `RedisConfig.java`: Redis bağlantısını ve RedisTemplate'i yapılandırır.

- **`service/`**: İş mantığını yöneten servis sınıflarını içerir.
    - `VerificationCodeService.java`: Doğrulama kodlarını üretir ve doğrular.

- **`controller/`**: HTTP isteklerini işlemek için kullanılan REST denetleyicisini içerir.
    - `AuthController.java`: Giriş ve doğrulama uç noktalarını yönetir.

## Temel Özellikler

- **Doğrulama Kodu Üretimi**:
    - 6 haneli bir doğrulama kodu üretir.
    - Kodu Redis'te 5 dakikalık bir süreyle saklar.

- **Kod Doğrulama**:
    - Kodu Redis'te saklanan değerle karşılaştırır.
    - Kodun geçerliliğine bağlı olarak başarı ya da başarısızlık sonucunu döndürür.

## Nasıl Çalışır?

1. **Giriş İsteği**:
    - Kullanıcı `userId` ile bir giriş isteği gönderir.
    - Sistem bir doğrulama kodu üretir ve bu kodu Redis'te `verification_code:{userId}` anahtarı altında saklar.
    - Doğrulama kodu kullanıcıya geri gönderilir (basitlik adına konsolda görüntülenir).

2. **Doğrulama İsteği**:
    - Kullanıcı, aldığı doğrulama kodunu gönderir.
    - Sistem, kodu Redis'ten alır ve gönderilen kodla eşleşip eşleşmediğini kontrol eder.
    - Kod doğruysa giriş başarılı olur, aksi halde başarısız olur.

## Örnek Kullanım

### 1. Giriş
```bash
POST /auth/login
Parametreler: userId=<KULLANICI_ID>

## Bu istek bir doğrulama kodu üretir ve Redis'te saklar.

### 2. Kodu Doğrulama

POST /auth/verify
Parametreler: userId=<KULLANICI_ID>&code=<KOD>
````

## Bu istek, kullanıcının gönderdiği kodu doğrular.

## Kullanılan Teknolojiler

* **Spring Boot**: Uygulamayı inşa etmek için kullanılan framework.
* **Spring Data Redis**: Redis'i Spring uygulamasıyla entegre etmek için kullanılır.
* **Redis**: Doğrulama kodlarını saklamak için kullanılan bellek içi veri yapısı deposu.

## Yapılandırma

Redis bağlantısı, `RedisConfig.java` içinde yapılandırılmıştır. `RedisTemplate`, anahtarlar için `StringRedisSerializer` ve değerler için `GenericJackson2JsonRedisSerializer` ile yapılandırılmıştır.

## Başlangıç

Uygulamayı yerel olarak çalıştırmak için:

1. Redis'in yüklü ve çalışır durumda olduğundan emin olun.
2. Reposu klonlayın.
3. Spring Boot uygulamasını tercih ettiğiniz IDE veya komut satırı ile derleyip çalıştırın.
4. Postman veya Curl gibi araçları kullanarak `/auth/login` ve `/auth/verify` uç noktalarıyla etkileşime geçin.

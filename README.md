# n11-talenthub-bootcamp-graduation-project

Proje Kapsamında hem backend hemde frontend geliştirilmiştir.  
Backend geliştirme içi Java Spring Boot , Frontend Kısmını için Angular CLI kullanılmıştır.
Database olarak PostgreSql Kullanılmıştır.

## Projeyi Çalıştırmak için 
### Frontend kısmı
- n11-finalproject-frontend ana dizinine gelerek önce `npm install` komutunu çalıştrarak gerekli paketler yüklenmektedir.
- ana dizinde iken  `ng serve` komutunu çalıştırarak frontend projesini localhost:4200 de yayınlanmaya başlanmakta.
- başka bir portta çalıştırmak için `ng serve --port XXXX` komutu çalıştırılmalıdır.
- api base url bağlantısı localhost:8082 ye yönlenmektedir. Base url bilgisini _src/environments_ içinde _environment.ts ve environment.prod.ts_ kısmından değiştirilebilmektedir._.prod.ts_ dosyayı projeyi build ederken kullanırç
- projeyi build etmek için `ng build` komutu çalıştırılmalıdır.  

### Backkend
- Backend kısmını bir ide ( intellj Idea kullanılmıştır ) kullanarak çalıştırmanız gerekmekte 
- Backend kısmını build/run etmeden önce  PostgreSql de N11_FINALPROJECT isimli bir veritabanı oluşturulması gerekmekte. Veritabanı bağlantı bilgileri _src/main/resources/applicationçproperites_ içinde bulunmaktadır
- proje localhost:8082 çalışmaktadır.
    


##### Projeler için dockerfile ve docker-compose dosyaları oluşturulmuştur ancak backend ve postgreSql birbiriyle iletişim kuramadığı için backend kısmı hata vermekte  

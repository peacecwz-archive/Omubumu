![https://lh3.googleusercontent.com/rBevqizm3C5s44tLoh3L6vHCuOV_tqOkuYbALXaukZGlkEqQcwXip7Ov6DHkpz0kwuKHeUDDqRX6RKVisPXyYA](https://lh3.googleusercontent.com/rBevqizm3C5s44tLoh3L6vHCuOV_tqOkuYbALXaukZGlkEqQcwXip7Ov6DHkpz0kwuKHeUDDqRX6RKVisPXyYA)

# OmuBumu

Simple survey application with images. Easy share and vote that or this. It has Android and Windows Phone mobile application. 

When I was 17, I developed this project and i enjoyed when i developed it :)

## Getting Started

First of all, you need to clone the project to your local machine and look Requirements

### Requirements

* Android Studio or Android Enviromment (for Android)
* Visual Studio and Windows & Windows Phone 8.1 SDK
* PHP 5.6
* MySQL

### Building

A step by step series of building that project

1. Create MySQL Database

2. Execute SQL Script on MySQL Database (Database Schema/omubumuapp.sql)

3. Change database connection strings (File: OmuBuMuAPI/OmuBuMuAPI/Core/Config.php and OmuBuMuAPI/OmuBuMuAPI/SifremiUnuttum/config.php)

4. Change password reset link [APIHelper.php](https://github.com/peacecwz/Omubumu/blob/478d7f615168cb2acfa6e043258cac47293b97c0/OmuBuMuAPI/OmuBuMuAPI/Helpers/APIHelper.php#L150)

5. Change API endpoint for Windows & Windows Phone 8.1 [DataClient.cs](https://github.com/peacecwz/Omubumu/blob/478d7f615168cb2acfa6e043258cac47293b97c0/OmuBumuUA/OmuBumu/OmuBumu.Shared/Common/DataClient.cs#L30)

6. Change API endpoint for Android [DataClient.java](https://github.com/peacecwz/Omubumu/blob/478d7f615168cb2acfa6e043258cac47293b97c0/Android/app/src/main/java/com/hsd/omubumu/Common/DataClient.java#L30)

7. Run Android, Windows 8.1 or Windows Phone 8.1 App and enjoy

## Demo

![http://1.bp.blogspot.com/-xdS8h3JFJ-0/Vc3D8gwMKSI/AAAAAAAAUQ0/0AAYxIU5vYA/s1600/O%2Bmu%2B%2BBu%2Bmu%2B.png](http://1.bp.blogspot.com/-xdS8h3JFJ-0/Vc3D8gwMKSI/AAAAAAAAUQ0/0AAYxIU5vYA/s1600/O%2Bmu%2B%2BBu%2Bmu%2B.png)

## Built With

* [PHP](http://php.net/) 
* [Slim Framework](https://www.slimframework.com/)
* [MySQL](https://www.mysql.com/)
* [Android](https://developer.android.com/)
* [Windows & Windows Phone](https://developer.microsoft.com/en-us/windows)

## Contributing

* If you want to contribute to codes, create pull request
* If you find any bugs or error, create an issue

## Contributors

* Kadir Can Kırkoyun [Github](https://github.com/kadircankirkoyun) [Twitter](https://twitter.com/kkirkoyun)
* Oğuzhan Akıncı [Github](https://github.com/oguzhanaknc) [Twitter](https://twitter.com/oguzhanaknc)

## License

This project is licensed under the MIT License

# Kantin Kejujuran

Kantin Kejujuran is simple website that consists of a store with some items for sale by students and balance box to store all the purchased money. 
Everyone is free to look around, sell, and buy items there. 
There is no shopkeeper there so everyone is also free to add or withdraw the money in the box. 
Every student is expected to be honest. 
They will add the balance with exactly the same amount of their bought item’s price. 
They will also just withdraw the balance if they sell something and know that their items are sold, with exactly the same amount of their sold item’s price.

## Features

* Registration
    * Input a student ID and password
    * An ID should consist of a 5 digits number from 0-9
    * Two last digits is the sum of three first digits
    * Only validated ID can register
    * The ID is not registered yet in the system
* Login
    * Insert student ID and password that is registered
    * Only registered student can login to the canteen
* Logout
* Store
    * Unregistered user can only view the listed items in the store
    * Only logged in user can add and buy any items in the store
    * Item have a name, image, description, price, and its created date
    * Item sorted in the store by created date (descending) and name (ascending)
* Balance Box
    * Unregistered user cannot see the balance box
    * Only logged in user can add and withdraw money (in Rupiah) to the balance box
    * The maximum amount can be add is unlimited
    * The maximum amount can be withdrawn is the current balance


## Getting Started

### Technologies

* Spring Boot
* PostgreSQL
* Tailwind CSS

### Development

* Clone this repository
```
git clone https://github.com/mfikriharyanto/kantin-kejujuran.git
```
* Creates PostgreSQL database
```
CREATE DATABASE kantinkejujujuran;
```
* Update `src/main/resources/application.properties`
```
# datasource properties
spring.datasource.url=jdbc:postgresql://localhost:5432/kantinkejujuran
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
```
* Install Tailwind CSS
```
npm install -D tailwindcss
npx tailwindcss init
```
* Configure template paths in tailwind.config.js file
```
module.exports = {
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {},
  },
  plugins: [],
}
```
* Add the Tailwind directives to `src/main/resources/static/css/input.css`
```
@tailwind base;
@tailwind components;
@tailwind utilities;
```
* Start the Tailwind CLI build process
```
npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/main.css --watch
```
* Start using Tailwind new HTML
```
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/main.css"/>
</head>
<body>
    <h1 class="text-3xl font-bold underline">Hello world!</h1>
</body>
</html>
```

## License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/mfikriharyanto/kantin-kejujuran/blob/master/LICENSE) file for details

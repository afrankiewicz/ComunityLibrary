<p align="center">
    <img src="src/images/logo.png" width="400">
</p>

## About the Project
Community Library goal is to enable neighbours to borrow books from each other. 
Idea behind the project was to make use of books that everyone has sitting on shelves, while making positive environmental impact and bringing communities together.

Comly API enables:
* adding books to virtual library
* searching for books available to borrow nearby
* leaving reviews to the users
* liking reviews


## Getting Started
To get a local copy up and running, please follow these simple steps.

### Prerequisites
Here is what you need to be able to run Comly:
* Java 17

## Development
### Setup
1. Clone the repo into a public GitHub repository.
```
git clone https://github.com/afrankiewicz/ComunityLibrary.git
```
2. Go to the project folder
```
cd CommunityLibrary
```
3. Set local env variable COMLY_GEOLOCATION_API_KEY. 

Get the <a href="https://openweathermap.org/api/geocoding-api">API key</a> value.


4. Run Comly
```
./mvnw spring-boot:run
```
5. Application runs on port 8080. 
We are still in development stage, as for now Comly is running on H2 in-memory database.


## API Reference

### USERS
* `POST /users/register` - Creates user.
```
{
    "firstName": "Jan",
    "lastName": "Kowalski",
    "email": "jan.kowalski@gmail.com",
    "password": "Tajnehaslo789",
    "city": "Warszawa",
    "street": "Marszalkowska"
}
```

### BOOKS
* `POST /books` - Adds book to the library.
```
{
    "isbn": "978-83-89405-00-5",
    "title": "Killing Commendatore",
    "author": "Haruki Murakami",
    "language": "PL"
}
```
* `GET /books/owned` - Gets list of owned books.
* `GET /books/borrowed` - Gets list of borrowed books.
* `GET /books/all` - Gets list of all available books.
* `PUT /books/{bookId}/borrow` - Borrows book with specified id.
* `PUT /books/{bookId}/return` - Returns book with specified id.

### COMMENTS
* `POST /comments/reviewee/{revieweeId}` - Leaves review for user with specified id.
```
{
    "text": "Everything was great."
}
```
* `GET /comments/{commentId}` - Gets comment with specified id.
* `PUT /comments/{commentId}` - Updates comment with specified id.
* `DELETE /comments/{commentId}` - Deletes comment with specified id.

### COMMENT LIKES
* `POST /likes/comments/{commentId}` - Likes a comment with specified id.
* `GET /likes/{commentLikeId}` - Gets comment like with specified id.
* `GET /likes/comments/{commentId}` - Gets all likes for comment with specified id.
* `DELETE /likes/{commentLikeId}` - Deletes comment like with specified id.




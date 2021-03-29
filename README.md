# ColorConfluence
Web application to find and manage decks and collectible cards from Magic The Gathering.

## Index
### [1. Introduction](#introduction)
### [2. ColorConfluence API](#colorconfluence-api)

## Introduction
## ColorConfluence API
In this section are listed below the available API requests ColorConfluience offer summarized. Further info clicking in the URLS. All of them refers to http://colorconfluence.ddns.net:8080:

URL | Params | Description
---- | ------- | ---------
/api/set/all | | Return all available card Sets
/api/card/count/name | name | Return the number of cards that matches the given name
/api/card/find/name | name | Return a list of cards that matches the given name with info summarized
/api/card/find | criteria | Return a list of cards that amtches with the specified criteria
/api/card/{id} | {id] | Return the detailed info of the card with the given id

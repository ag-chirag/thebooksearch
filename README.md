thebooksearch
=============

Java based Search Engine



The idea of this project is to enable the user to run queries and get search results from books in same way as Google or any other web search engine return results to user from web pages. The books can be uploaded into the SQL database with the help of simple Java based UI.

The uploaded book is indexed and reverse indexed and stored into the database. When the user fires a query the engine calculates a score for the appropriate pages and returns the page numbers and book name as result in decreasing order of  relevance of the pages(to that query).


Apart from “General Search” the engine supports four other types of searches:

Asterik Search:  If a user gives a search token followed by an asterisk (for e.g. auto*) then all the words in the database which include this pattern (for e.g. auto) will be included in the query (for e.g. automata, autobiography, automatic etc.). 

Boosted Search:  Boosted term lets the user boost the weight of a specific token(of his choice) in the query by a specific factor of his choice. For example peer^10 protocol, as a search query, will increase the weight of token “peer” by the factor of ten. The boost factor can be negative also.

Boolean Search:  It supports Boolean functions AND and NOT . Apart from these it also supports +(for required terms) and – ( for terms not allowed) operator.

Wildcard Search: A wildcard search, for example, te_t will include all the words like text, test, tent etc. in the query.


User is allowed to view his history, change book names, upload or delete a book to or from database.

The UI and entire programming is in JAVA. The database used is MySql database.

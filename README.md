# newsback-api

Backend for the newsfront app

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Installation

### Configure profiles
```
{
  :profiles/dev  {:env {:database-url "postgresql://<user_name>:<password>@<host>/<db_name>"}},
  :profiles/test  {:env {:database-url "postgresql://<user_name>:<password>@<host>/<db_name>"}},
}
```

## Running

To start a web server for the application, run:

    lein run

# Tracking File

The user interactions are stored into a tracking file.

- The tracking files are stored into a S3 bucket into the path ``tracks/<yyyy>/<mm>/<dd>/``
- The tracking files are in CSV format, with the following columns:
    - **time**: date/time in millisecond when the event was occured.
    - **tenantid**: ID of the site where the event fired
    - **correlationid**: ID assigned all event correlated.
    - **deviceid**: ID of the device (phone, browser) that fired the event.
    - **accountid**: ID of the user who fired the event. This information is provided if user is logged in
    - **merchantid**: ID of the merchant associated with the event.
    - **productid**: ID of the product associated with the event
    - **page**: Name of the page where the event was fired.
    - **event**: Name of the event fired. The different values are:
        - **load**: A screen is loaded
        - **order**: A order is completed
        - **sales**: A product is puchased
        - **view**: A product is viewed
        - **share**: A product is shared
        - **chat**: A customer start a chat with vendor about a product
    - **value**: Value attached with the event.
    - **ip**: IP of the device that fired the event
    - **long**: Longitude where the event was fired
    - **lat**: Latitude where the event was fired
    - **referer**: URL of the referer page
    - **bot**: `true` if this event was fired by a bot
    - **ua**: User Agent
    - **url**: URL where the event was fired
    - **impressions**: IDs of the product printed when the event was fired. IDs are separated by |
    - **devicetype**: Type of device. The different values are:
        - *app*: Mobile application
        - *mobile*: Mobile web
        - *desktop*: Desktop browser

# Aggregate files

Aggregates are files generated from the tracking files, that compiles data. Each aggregate has:

- The daily aggregate: aggregation for a given day
- The monthly aggregate: aggregation for a given month

### The Visit aggregate

- Description: Count the number of visits on products
- Daily aggregate file: ``aggregates/daily/<yyyy>/<mm>/<dd>/visit.csv``
- Daily aggregate file: ``aggregates/monthly/<yyyy>/<mm>/visit.csv``
- File Format
    - **time**: date/time in millisecond when the event was occured.
    - **tenantid**: ID of the site where the event fired
    - **merchantid**: ID of the merchant associated with the event.
    - **productid**: ID of the product associated with the event
    - **count**: Total number of views

### The Share aggregate

- Description: Count the number of shares on products
- Daily aggregate file: ``aggregates/daily/<yyyy>/<mm>/<dd>/share.csv``
- Daily aggregate file: ``aggregates/monthly/<yyyy>/<mm>/share.csv``
- File Format
    - **time**: date/time in millisecond when the event was occured.
    - **tenantid**: ID of the site where the event fired
    - **merchantid**: ID of the merchant associated with the event.
    - **productid**: ID of the product associated with the event
    - **count**: Total number of shares

### The chat aggregate

- Description: Count the number of chat interactions on products
- Daily aggregate file: ``aggregates/daily/<yyyy>/<mm>/<dd>/chat.csv``
- Daily aggregate file: ``aggregates/monthly/<yyyy>/<mm>/chat.csv``
- File Format
    - **time**: date/time in millisecond when the event was occured.
    - **tenantid**: ID of the site where the event fired
    - **merchantid**: ID of the merchant associated with the event.
    - **productid**: ID of the product associated with the event
    - **count**: Total number of chat interaction

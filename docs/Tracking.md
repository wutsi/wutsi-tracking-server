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
        - *APP*: Mobile application
        - *MOBILE*: Mobile web
        - *DESKTOP*: Desktop browser
    - **source**: Source of the traffic. The values are
        - **DIRECT**
        - **EMAIL**
        - **FACEBOOK**
        - **WHATSTAPP**
        - **INSTAGRAM**
        - **SEO**

# Aggregate files

Aggregates are files generate from tracking files, that contains information about a given metric. These files are
geneerated every day at 1:00 AM.

For each metric, the following aggregates are created:

- The daily aggregate: contains metric data for the previous day. Stored
  to: ``aggregates/daily/<yyyy>/<mm>/<dd>/<metric-name>.csv``
- The monthly aggregate: contains metric data for the current month Stored
  to: ``aggregates/monthly/<yyyy>/<mm><metric-name>.csv``
- The yearly aggregate: contains metric data for the current year Stored
  to: ``aggregates/yearly/<yyyy>/<metric-name>.csv``
- The overall aggregate: contains the metric data for the whole lifespan Stored
  to: ``aggregates/overall/<metric-name>.csv``

The aggregate file is a CSV file with the following columns:

- **time**: date/time in millisecond when the event was occured. - **tenantid**: ID of the site where the event fired
- **merchantid**: ID of the merchant associated with the event. - **productid**: ID of the product associated with the
  event - **value**: Value of the metric

### The View aggregate

- Metric Name: `view`
- Description: Count the number of views on products.

### The Share aggregate

- Metric Name: `share`
- Descriptor: Count the number of type product are shared.

### The Chat aggregate

- Metric Name: `chat`
- Descriptor: Count the number of time chat interactions are started on products.

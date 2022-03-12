# Tracking File Format

The user interactions are stored into CSV files having the following columns:

## File Format

- **time**: date/time in millisecond when the event was occured.
- **tenantid**: ID of the site where the event fired
- **correlationid**: ID assigned all event correlated.
- **deviceid**: ID of the device (phone, browser) that fired the event.
- **accountid**: ID of the user who fired the event. This information is provided if user is logged in
- **merchantid**: ID of the merchant associated with the event.
- **productid**: ID of the product associated with the event
- **page**: Name of the page where the event was fired.
- **event**: Name of the event fired.
- **value**: Value attached with the event.
- **ip**: IP of the device that fired the event
- **long**: Longitude where the event was fired
- **lat**: Latitude where the event was fired
- **referer**: URL of the referer page
- **bot**: `true` if this event was fired by a bot
- **ua**: User Agent
- **url**: URL where the event was fired
- **impressions**: IDs of the product printed when the event was fired. IDs are separated by |

## Tracking Events

- **load**: A screen is loaded
- **sales**: A product is puchased
- **click**: User clicks on a button

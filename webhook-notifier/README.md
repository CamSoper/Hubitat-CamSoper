# Webhook Notifier

This is a thing I adapted to create a "speech" device in Hubitat that pushes the message to a webhook. 

What webhook? Any webhook that expects a simple payload. So probably not Discord or Slack or anything that requires tokens and such. I'm thinking more like the [IFFFT Maker Webhook Service](https://ifttt.com/maker_webhooks), [Microsoft Flow](https://flow.microsoft.com), a custom CORE rule in SmartThings, or an [Azure Function](https://docs.microsoft.com/azure/azure-functions/) or [Logic App](https://docs.microsoft.com/azure/logic-apps/). Or pretty much anything you want.

## Installation

1. Copy/paste the driver code (webhooknotifier.groovy) into a new driver in the Hubitat settings and save.
2. Copy/paste the app code (webhooknotifierconnect.groovy) into a new app in the Hubitat settings and save.
3. Install a new user app using the app code with the following parameters:
  * **Webhook URL** - The URL you're posting the webhook to with an HTTP POST.
  * **JSON Property Name** - The name of the property holding the message in the JSON payload. For IFTTT, this will be `value1`. For other services, it varies. Often, as in the case of Azure Functions and Logic Apps, you can name it yourself.
  * **Prefix Text** - If you want to append something like "Hubitat says" or "Message from Hubitat". A space will be inserted between this and your payload.
  * **Replace Hyphens** - I like to use the Maker API for a lot of personal projects, and the only way I've found to pass spaces in a string for speaking is as hyphens. So I added this switch to replace the hyphens with spaces so the text is more readable.

That's it! Now you can use all kinds of services to sling your notifications. I have personally tested it with IFTTT, SmartThings via a custom rule in CORE, and Azure Logic Apps.

Thanks to @rayzurbock for the [Pushbullet code](https://github.com/rayzurbock/hubitat/tree/master/pushbullet) I repurposed.

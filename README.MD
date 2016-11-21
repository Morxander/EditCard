# EditCard

![EditCard](http://i.imgur.com/xKAc3GE.gif)

Custom EditText For Android To Enter Credit Card Numbers

### Download

Download via Gradle:

```gradle
compile 'morxander.editcard:EditCard:1.0.0'
```

### Usage

```xml
<morxander.editcard.EditCard
        android:id="@+id/card"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

In activity class:
```java
EditCard edit_card = (EditCard) findViewById(R.id.card);
edit_card.getCardNumber(); // Get the card number
edit_card.isValid(); // Is the card number valid
edit_card.getCardType(); // Get the card type
```

For now we only support the following card types :

* VISA
* MASTERCARD
* American Express
* DISCOVER
* JCB
* Discover

### License 
GNU GENERAL PUBLIC LICENSE 3
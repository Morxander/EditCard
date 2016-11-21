# EditCard

![EditCard](http://imgur.com/a/Ta8IM)

Custom EditText For Android To Enter Credit Card Numbers

### Download

Download via Gradle:

```gradle
compile ''
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



### License 
GNU GENERAL PUBLIC LICENSE 3
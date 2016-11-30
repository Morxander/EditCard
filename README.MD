# EditCard
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-EditCard-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/4704)

![EditCard](https://raw.githubusercontent.com/Morxander/EditCard/master/demo.gif)

Custom EditText For Android To Enter Credit Card Numbers

### Download

Download via Gradle:

```gradle
compile 'morxander.editcard:EditCard:1.0.1'
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

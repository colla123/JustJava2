/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava2;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.justjava2.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText namefield = (EditText)findViewById(R.id.name_field);
        String name = namefield.getText().toString();

        //Log.v("MainActivity ", "Name: " + name );

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        //Log.v("MainActivity ", "Has whipped cream: " + hasWippedCream);

        //Log.v("MainActivity ", "Has Chocolate: " + hasChocolate);


        int price = calculatePrice(hasWippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWippedCream, hasChocolate);

        // send content to message app
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:"));
        intent.putExtra("sms_body", priceMessage);
        //intent.setType("vnd.android-dir/mms-sms");
        startActivity(intent);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //  send email
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for:a " + name);
//        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

            displayMessage(priceMessage);

//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("geo:47.6, -122.3"));
//                if (intent.resolveActivity(getPackageManager()) != null)
//                {
//                    startActivity(intent);
//                }

    }

    private int calculatePrice(boolean addWippedCream, boolean addChocolate)
    {
        int basePrice = 5;

        if (addWippedCream)
        {
            basePrice = basePrice + 1;
        }

        if (addChocolate)
        {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate)
    {
        //String priceMessage = "\n Name: " + name;
        String priceMessage =  "";

        //String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage = priceMessage + "\n" +  getString(R.string.name) + "\t" + "\t" + name;

        priceMessage = priceMessage + "\n" +  getString(R.string.chocolate) + "\t" + "\t" + addChocolate;
        //priceMessage = priceMessage + "\n Add Whipped Cream? " + addWhippedCream;

        priceMessage = priceMessage + "\n" +  getString(R.string.whipped_cream) + "\t" + "\t" + addWhippedCream;
        //priceMessage = priceMessage + "\n Add Chocolate? " + addChocolate;

        priceMessage = priceMessage + "\n" +  getString(R.string.quantity) + "\t" + "\t" + quantity;
        //priceMessage = priceMessage + "\n Quantity: " + quantity;

        priceMessage = priceMessage + "\n" +  getString(R.string.order_summary_price) + "\t"+ "\t" + price;
        //priceMessage = priceMessage + "\n Total: $" + price;

        // طريقة اضافة الترجمة لملف الجافا
        priceMessage = priceMessage + "\n" +  getString(R.string.thank_you);
        //priceMessage = priceMessage + "\n Thank you!";
        return priceMessage;


    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

        /**
         * This method displays the given text on the screen.
         */
        private void displayMessage(String message) {
            TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
            orderSummaryTextView.setText(message);
        }

}
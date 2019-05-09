package com.example.stephenlau.testlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import me.riddhimanadib.formmaster.FormBuilder;
import me.riddhimanadib.formmaster.model.BaseFormElement;
import me.riddhimanadib.formmaster.model.FormElementPickerDate;
import me.riddhimanadib.formmaster.model.FormElementPickerMulti;
import me.riddhimanadib.formmaster.model.FormElementPickerSingle;
import me.riddhimanadib.formmaster.model.FormElementPickerTime;
import me.riddhimanadib.formmaster.model.FormElementSwitch;
import me.riddhimanadib.formmaster.model.FormElementTextEmail;
import me.riddhimanadib.formmaster.model.FormElementTextMultiLine;
import me.riddhimanadib.formmaster.model.FormElementTextNumber;
import me.riddhimanadib.formmaster.model.FormElementTextPassword;
import me.riddhimanadib.formmaster.model.FormElementTextPhone;
import me.riddhimanadib.formmaster.model.FormElementTextSingleLine;
import me.riddhimanadib.formmaster.model.FormHeader;

public class FormMasterActivity extends BaseActivity {

    private static final int TAG_EMAIL = 12;
    private static final int TAG_PASSWORD = 2234;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.confirmButton)
    AppCompatButton confirmButton;

    private FormBuilder mFormBuilder;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_form_master;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mFormBuilder = new FormBuilder(this, mRecyclerView);

        // declare form elements
        FormHeader header = FormHeader.createInstance("Personal Info");

        FormElementTextEmail element1 = FormElementTextEmail.createInstance().setTag(TAG_EMAIL).setTitle("Email").setRequired(true);

        FormElementTextPassword element2 = FormElementTextPassword.createInstance().setTag(TAG_PASSWORD).setTitle("Password").setRequired(true);

        // email input
        FormElementTextEmail element3 = FormElementTextEmail.createInstance().setTitle("Email").setHint("Enter Email");

        // phone number input
        FormElementTextPhone element4 = FormElementTextPhone.createInstance().setTitle("Phone").setValue("+8801712345678");

        // single line text input
        FormElementTextSingleLine element5 = FormElementTextSingleLine.createInstance().setTitle("Location").setValue("Dhaka");

        // multi line text input (default 4)
        FormElementTextMultiLine element6 = FormElementTextMultiLine.createInstance().setTitle("Address");

        // number element input
        FormElementTextNumber element7 = FormElementTextNumber.createInstance().setTitle("Zip Code").setValue("1000");

        // date picker input
        FormElementPickerDate element8 = FormElementPickerDate.createInstance().setTitle("Date").setDateFormat("MMM dd, yyyy");

        // time picker input
        FormElementPickerTime element9 = FormElementPickerTime.createInstance().setTitle("Time").setTimeFormat("KK hh");

        // password input
        FormElementTextPassword element10 = FormElementTextPassword.createInstance().setTitle("Password").setValue("abcd1234");

        // switch input
        FormElementSwitch element11 = FormElementSwitch.createInstance().setTitle("Frozen?").setSwitchTexts("Yes", "No");

        // single item picker input
        List<String> fruits = new ArrayList<>();
        fruits.add("Banana");
        fruits.add("Orange");
        fruits.add("Mango");
        fruits.add("Guava");

        FormElementPickerSingle element12 = FormElementPickerSingle.createInstance().setTitle("Single Item").setOptions(fruits).setPickerTitle("Pick any item");

        List<BaseFormElement> formItems = new ArrayList<>();
        formItems.add(header);
        formItems.add(element1);
        formItems.add(element2);
        formItems.add(element3);
        formItems.add(element4);
        formItems.add(element5);
        formItems.add(element6);
        formItems.add(element7);
        formItems.add(element8);
        formItems.add(element9);
        formItems.add(element10);
        formItems.add(element11);
        formItems.add(element12);

        mFormBuilder.addFormElements(formItems);

        confirmButton.setOnClickListener(v -> {

            if (mFormBuilder.isValidForm()) {
                BaseFormElement loginElement = mFormBuilder.getFormElement(TAG_EMAIL);
                BaseFormElement passwordElement = mFormBuilder.getFormElement(TAG_PASSWORD);
                Toast.makeText(FormMasterActivity.this, "Do whatever you want with this data\n" + loginElement.getValue() + "\n" + passwordElement.getValue(), Toast.LENGTH_SHORT).show();

                // getFormElementIterator
                StringBuilder stringBuilder = new StringBuilder();
//                for (Iterator<BaseFormElement> iterator = mFormBuilder.getFormElementIterator(); iterator.hasNext(); ) {
//                    BaseFormElement baseFormElement = iterator.next();
//                    stringBuilder.append(baseFormElement.getTag());
//                    stringBuilder.append(":");
//                    stringBuilder.append(baseFormElement.getValue());
//                }
                Toast.makeText(FormMasterActivity.this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();

                // convertToDataMap
//                Toast.makeText(FormMasterActivity.this, mFormBuilder.convertToDataMap().toString(), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(FormMasterActivity.this, "Invalid form data", Toast.LENGTH_SHORT).show();
            }

        });
    }


}

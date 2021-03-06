package com.ood.waterball.teampathy.Controllers.MyUtils.Dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;


public class PostingDialogBuilder extends AlertDialog.Builder {
    protected Context context;
    protected int confirmBTNid;
    protected int cancelBTNid;
    protected String confirmStr;
    protected String cancelStr;
    protected String cancelDialogContentString;
    protected View view;
    protected AlertDialog dialog;
    protected int iconId;
    protected View.OnClickListener confirmBtnListener;
    protected View.OnClickListener cancelBtnListener;

    public PostingDialogBuilder(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public PostingDialogBuilder setConfirmButtonId(int confirmBTNid) {
        this.confirmBTNid = confirmBTNid;
        return this;
    }

    public PostingDialogBuilder setCancelButtonId(int cancelBTNid) {
        this.cancelBTNid = cancelBTNid;
        return this;
    }

    public PostingDialogBuilder setCancelDialogContentString(String cancelDialogContentString) {
        this.cancelDialogContentString = cancelDialogContentString;
        return this;
    }

    public PostingDialogBuilder setConfirmString(String confirmStr) {
        this.confirmStr = confirmStr;
        return this;
    }

    public PostingDialogBuilder setCancelString(String cancelStr) {
        this.cancelStr = cancelStr;
        return this;
    }

    @Override
    public PostingDialogBuilder setIcon(int iconId) {
        super.setIcon(iconId);
        this.iconId = iconId;
        return this;
    }

    @Override
    public PostingDialogBuilder setView(View view) {
        super.setView(view);
        this.view = view;
        return this;
    }

    @Override
    public AlertDialog create() {
        dialog = super.create();
        setButtonsListener();
        return dialog;
    }

    public void setConfirmButtonListener(View.OnClickListener listener){
        this.confirmBtnListener = listener;
    }

    public void setCancelButtonListener(View.OnClickListener listener){
        this.cancelBtnListener = listener;
    }

    private void setButtonsListener(){
        Button confirmButton = (Button) view.findViewById(confirmBTNid);
        Button cancelButton = (Button) view.findViewById(cancelBTNid);

        if (confirmBtnListener == null)  // it won't always be null , because users can set listener before creating.
            confirmBtnListener = getConfirmBtnListener(dialog); // if it hasn't been set , get it from subclass method.
        if ( cancelBtnListener == null) // it won't always be null , because users can set listener before creating.
            cancelBtnListener = getCancelBtnListener(dialog);  // if it hasn't been set , use default.

        if ( confirmBtnListener == null)  // it there is no subclass overriding getConfirmBtnListener() method , use default
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        else
            confirmButton.setOnClickListener(confirmBtnListener);

        if ( cancelBtnListener == null )
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        else
            cancelButton.setOnClickListener(cancelBtnListener);
    }

    protected View.OnClickListener getConfirmBtnListener(final AlertDialog currentDialog){
        return null;
    }

    protected View.OnClickListener getCancelBtnListener(final AlertDialog currentDialog){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                if ( iconId != 0 )
                    builder.setIcon(iconId);
                if ( cancelDialogContentString != null )
                    builder.setTitle(cancelDialogContentString);
                else
                    builder.setTitle("Are you going to close?");
                String confirm = confirmStr == null ? "Finish" : confirmStr;
                String cancel = cancelStr == null ? "Cancel" : cancelStr;

                builder.setPositiveButton( confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        currentDialog.dismiss();
                    }
                });
                builder.setNegativeButton( cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).show();
            }
        };
    }



}

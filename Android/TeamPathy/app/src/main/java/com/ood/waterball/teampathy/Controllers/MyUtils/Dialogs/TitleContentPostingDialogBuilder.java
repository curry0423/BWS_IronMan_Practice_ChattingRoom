package com.ood.waterball.teampathy.Controllers.MyUtils.Dialogs;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class TitleContentPostingDialogBuilder extends PostingDialogBuilder {

    protected int titleTextInputEditTextId;
    protected int contentTextInputEditTextId;
    protected int scrollviewId;
    protected int errorTextViewId;
    protected boolean errorDetect = true;
    protected onFinishListener onFinishListener;
    protected OnDetectListener onDetectListener;

    public TitleContentPostingDialogBuilder(@NonNull Context context) {
        super(context);
    }


    public TitleContentPostingDialogBuilder setTitleTextInputEditTextId(int titleTextInputEditTextId) {
        this.titleTextInputEditTextId = titleTextInputEditTextId;
        return this;
    }


    public TitleContentPostingDialogBuilder setOnDetectListener(OnDetectListener onDetectListener) {
        this.onDetectListener = onDetectListener;
        return this;
    }

    public TitleContentPostingDialogBuilder setScrollviewId(int scrollviewId) {
        this.scrollviewId = scrollviewId;
        return this;
    }

    public TitleContentPostingDialogBuilder setOnFinishListener(TitleContentPostingDialogBuilder.onFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
        return this;
    }

    public TitleContentPostingDialogBuilder setContentTextInputEditTextId(int contentTextInputEditTextId) {
        this.contentTextInputEditTextId = contentTextInputEditTextId;
        return this;
    }

    public TitleContentPostingDialogBuilder setErrorTextViewId(int errorTextViewId) {
        this.errorTextViewId = errorTextViewId;
        return this;
    }

    public TitleContentPostingDialogBuilder setErrorDetect(boolean errorDetect) {
        this.errorDetect = errorDetect;
        return this;
    }

    @Override
    protected View.OnClickListener getConfirmBtnListener(final AlertDialog currentDialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = (TextView) view.findViewById(titleTextInputEditTextId);
                TextView content = (TextView) view.findViewById(contentTextInputEditTextId);
                ScrollView scrollView = (ScrollView) view.findViewById(scrollviewId);

                final TextView error = (TextView) view.findViewById(errorTextViewId);

                if (error.getVisibility() == View.VISIBLE)
                    error.setVisibility(View.GONE);

                if ( onDetectListener == null )
                    onDetectListener = new OnDetectListener() {
                        @Override
                        public boolean onTextEmptyReport(int errorViewId) {
                            if (errorViewId == titleTextInputEditTextId)
                            {
                                error.setText("Title should not be empty.");
                                error.setVisibility(View.VISIBLE);
                                return false;
                            }

                            else if (errorViewId == contentTextInputEditTextId)
                            {
                                error.setText("Content should not be empty.");
                                error.setVisibility(View.VISIBLE);
                                return false;
                            }

                            return true;
                        }

                        @Override
                        public boolean onElseDetect() {return true;}

                        @Override
                        public boolean onDetectLength(int viewId, int length) { return true; }

                    };
                boolean titleAvailable = true;
                boolean contentAvailable = true;

                if ( title != null )
                    if ( title.getText().toString().isEmpty() )
                        titleAvailable = onDetectListener.onTextEmptyReport(titleTextInputEditTextId);

                if ( content != null )
                    if ( content.getText().toString().isEmpty() )
                        contentAvailable = onDetectListener.onTextEmptyReport(contentTextInputEditTextId);

                if (errorDetect)
                    if (  titleAvailable && contentAvailable &&
                            onDetectListener.onTextEmptyReport(contentTextInputEditTextId) &&
                            onDetectListener.onDetectLength(titleTextInputEditTextId,title.getText().length()) &&
                            onDetectListener.onDetectLength(contentTextInputEditTextId,content.getText().length()) &&
                            onDetectListener.onElseDetect()  )
                    {
                        onFinishListener.onFinish(title.getText().toString(),content.getText().toString());
                        currentDialog.dismiss();
                    }
                    else if (scrollView != null)
                        scrollView.fullScroll(ScrollView.FOCUS_UP);

            }
        };
    }


    public interface OnDetectListener {
        public boolean onTextEmptyReport(int errorViewId); // if any of texts is empty , it will be pass as a parameter in the function
        public boolean onElseDetect();  // add additional stuffs here to detect else things and pass a boolean result to decide if an error occurs.
        public boolean onDetectLength(int viewId , int length); // to detect whether all views' lengths are correct and return a boolean result to decide if an error occurs.
    }

    public interface onFinishListener{
        public void onFinish(String title,String content);
    }
}

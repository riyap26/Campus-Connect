package com.example.campusconnect;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Hyperlink;

public class ObservableHyperLink extends Hyperlink implements ObservableValue<Hyperlink> {
    private Hyperlink link;
    public ObservableHyperLink(Hyperlink link){
        this.link = link;
    }
    public void addListener(ChangeListener<? super Hyperlink> listener){
        return;
    }

    public void removeListener(ChangeListener<? super Hyperlink> listener){
        return;
    }

    public Hyperlink getValue(){
        return link;
    }

    public void addListener(InvalidationListener listener){
        return;
    }

    public void removeListener(InvalidationListener listener){
        return;
    }

}

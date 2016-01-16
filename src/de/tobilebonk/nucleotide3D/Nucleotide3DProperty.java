package de.tobilebonk.nucleotide3D;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Dappsen on 10.01.2016.
 */
public class Nucleotide3DProperty extends ObjectProperty<Nucleotide3DTemplate> {

    private Nucleotide3DTemplate nucleotide3D;

    public Nucleotide3DProperty(Nucleotide3DTemplate nucleotide3D){
        this.nucleotide3D = nucleotide3D;
    }

    @Override
    public Nucleotide3DTemplate get() {
        return nucleotide3D;
    }

    @Override
    public void set(Nucleotide3DTemplate value) {
        this.nucleotide3D = value;
    }

    @Override
    public void bind(ObservableValue<? extends Nucleotide3DTemplate> observable) {
        this.bind(observable);
    }

    @Override
    public void unbind() {
        this.unbind();
    }

    @Override
    public boolean isBound() {
        return false;
    }

    @Override
    public Object getBean() {
        return this.getBean();
    }

    @Override
    public String getName() {
        return this.getName();
    }

    @Override
    public void addListener(ChangeListener<? super Nucleotide3DTemplate> listener) {
        this.addListener(listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Nucleotide3DTemplate> listener) {
        this.removeListener(listener);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        this.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        this.removeListener(listener);
    }
}

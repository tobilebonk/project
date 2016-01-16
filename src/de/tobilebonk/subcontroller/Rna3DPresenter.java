package de.tobilebonk.subcontroller;

import de.tobilebonk.Model;
import de.tobilebonk.subview.Rna3DView;
import javafx.beans.property.SimpleBooleanProperty;


/**
 * Created by Dappsen on 10.01.2016.
 */
public class Rna3DPresenter {

    private Rna3DView view;;
    private Model model;

    public Rna3DPresenter(Model model, Rna3DView view) {

        this.view = view;
        this.model = model;

        //show-checkboxes
        view.getCheckBoxShowA().setOnAction(event -> {
            if (model != null) {
                if (view.getCheckBoxShowA().isSelected()) {
                    model.getAdenine3DList().forEach(n -> view.add3DNucleotide(n.getValue()));
                    initOnNucleotideClickListener();
                } else {
                    model.getAdenine3DList().forEach(n -> view.remove3DNucleotide(n.getValue()));
                }
            }
        });
        view.getCheckBoxShowC().setOnAction(event -> {
            if (model != null) {
                if (view.getCheckBoxShowC().isSelected()) {
                    model.getCytosin3DList().forEach(n -> view.add3DNucleotide(n.getValue()));
                    initOnNucleotideClickListener();
                } else {
                    model.getCytosin3DList().forEach(n -> view.remove3DNucleotide(n.getValue()));
                }
            }
        });
        view.getCheckBoxShowG().setOnAction(event -> {
            if (model != null) {
                if (view.getCheckBoxShowG().isSelected()) {
                    model.getGuanine3DList().forEach(n -> view.add3DNucleotide(n.getValue()));
                    initOnNucleotideClickListener();
                } else {
                    model.getGuanine3DList().forEach(n -> view.remove3DNucleotide(n.getValue()));
                }
            }
        });
        view.getCheckBoxShowU().setOnAction(event -> {
            if (model != null) {
                if (view.getCheckBoxShowU().isSelected()) {
                    model.getUracil3DList().forEach(n -> view.add3DNucleotide(n.getValue()));
                    initOnNucleotideClickListener();
                } else {
                    model.getUracil3DList().forEach(n -> view.remove3DNucleotide(n.getValue()));
                }
            }
        });
        //draw bonds checkboxes
        view.getCheckBoxPhosphorBonds().disableProperty().bind(
                view.getCheckBoxShowA().selectedProperty().not().
                        or(view.getCheckBoxShowC().selectedProperty().not().
                                or(view.getCheckBoxShowG().selectedProperty().not().
                                        or(view.getCheckBoxShowU().selectedProperty().not()))));
        view.getCheckBoxPhosphorBonds().setOnAction(event -> {
            if (view.getCheckBoxPhosphorBonds().isSelected()) {
                if (model.getNucleotide3DAllSortedList().size() > 1) {
                    for (int i = 0; i < model.getNucleotide3DAllSortedList().size() - 1; ++i) {
                        model.getNucleotide3DAllSortedList().get(i).getValue()
                                .connectPhosphorToPhosphorOf(model.getNucleotide3DAllSortedList().get(i + 1).getValue());
                    }
                }
            } else {
                for (int i = 0; i < model.getNucleotide3DAllSortedList().size() - 1; ++i) {
                    model.getNucleotide3DAllSortedList().get(i).getValue().disconnectPhosphorFromPhosphor();
                }
            }
        });
        view.getCheckBoxSugarPhosphorBonds().disableProperty().bind(view.getCheckBoxPhosphorBonds().disabledProperty());
        view.getCheckBoxSugarPhosphorBonds().setOnAction(event -> {
            if (view.getCheckBoxSugarPhosphorBonds().isSelected()) {
                if (model.getNucleotide3DAllSortedList().size() > 1) {
                    for (int i = 0; i < model.getNucleotide3DAllSortedList().size() - 1; ++i) {
                        model.getNucleotide3DAllSortedList().get(i).getValue()
                                .connectSugarToPhosphorOf(model.getNucleotide3DAllSortedList().get(i + 1).getValue());
                    }
                }
            } else {
                for (int i = 0; i < model.getNucleotide3DAllSortedList().size() - 1; ++i) {
                    model.getNucleotide3DAllSortedList().get(i).getValue().disconnectSugarFromPhosphor();
                }
            }
        });

        //coloring checkboxes
        view.getCheckBoxColorA().disableProperty().bind(view.getCheckBoxShowA().selectedProperty().not());
        view.getCheckBoxColorA().setOnAction(event -> {
            if (view.getCheckBoxColorA().isSelected()){
                model.getAdenine3DList().forEach(n -> {
                    n.getValue().setColoring();
                });
            }else{
                model.getAdenine3DList().forEach(n -> {
                    n.getValue().resetColoring();
                });
            }
        });
        view.getCheckBoxColorC().disableProperty().bind(view.getCheckBoxShowC().selectedProperty().not());
        view.getCheckBoxColorC().setOnAction(event -> {
            if(view.getCheckBoxColorC().isSelected()){
                model.getCytosin3DList().forEach(n -> {
                    n.getValue().setColoring();
                });
            } else {
                model.getCytosin3DList().forEach(n -> {
                    n.getValue().resetColoring();
                });
            }
        });
        view.getCheckBoxColorG().disableProperty().bind(view.getCheckBoxShowG().selectedProperty().not());
        view.getCheckBoxColorG().setOnAction(event -> {
            if (view.getCheckBoxColorG().isSelected()){
                model.getGuanine3DList().forEach(n -> {
                    n.getValue().setColoring();
                });
            }else{
                model.getGuanine3DList().forEach(n -> {
                    n.getValue().resetColoring();
                });
            }
        });
        view.getCheckBoxColorU().disableProperty().bind(view.getCheckBoxShowU().selectedProperty().not());
        view.getCheckBoxColorU().setOnAction(event -> {
            if(view.getCheckBoxColorU().isSelected()){
                model.getUracil3DList().forEach(n -> {
                    n.getValue().setColoring();
                });
            }else{
                model.getUracil3DList().forEach(n -> {
                    n.getValue().resetColoring();
                });
            }
        });
    }

    private void initOnNucleotideClickListener(){
        model.getNucleotide3DAllSortedList().forEach(n -> {
            n.getValue().getNucleotideGroup().setOnMouseClicked(click -> {
                n.getValue().switchColoring();
            });
        });
    }

    public Rna3DView getView(){
        return this.view;
    }

}
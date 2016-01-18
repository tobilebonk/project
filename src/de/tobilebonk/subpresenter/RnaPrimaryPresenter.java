package de.tobilebonk.subpresenter;

import de.tobilebonk.Model;
import de.tobilebonk.subview.RnaPrimaryView;
import de.tobilebonk.subview.SubView;

/**
 * Created by Dappsen on 18.01.2016.
 */
public class RnaPrimaryPresenter implements Subpresenter{

    SubView view;
    Model model;

    public RnaPrimaryPresenter(Model model, RnaPrimaryView view) {

        this.view = view;
        this.model = model;

        view.addAllResiduesToView(model.getModeledResidues());

    }


    @Override
    public SubView getSubView() {
        return view;
    }
}

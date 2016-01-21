package de.tobilebonk.subpresenter;

import de.tobilebonk.subview.SubView;

/**
 * Created by Dappsen on 18.01.2016.
 */
public interface Subpresenter {

    public SubView getSubView();
    public void setSubView(SubView view);
}
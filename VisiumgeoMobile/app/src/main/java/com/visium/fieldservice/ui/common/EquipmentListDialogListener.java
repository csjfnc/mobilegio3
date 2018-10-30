package com.visium.fieldservice.ui.common;

import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.equipament.EquipmentType;

/**
 * Created by ltonon on 20/10/2016.
 */
public interface EquipmentListDialogListener {

    void onAddItemButtonClicked(EquipmentType type, Post post, Object o);
    void onEditItemClicked(EquipmentType type, Post post, Object o);
    void onDeleteItemClicked(EquipmentType type, Post post, Object o);
    void onListItemEquipament(EquipmentType type, Post post, Object o);
}

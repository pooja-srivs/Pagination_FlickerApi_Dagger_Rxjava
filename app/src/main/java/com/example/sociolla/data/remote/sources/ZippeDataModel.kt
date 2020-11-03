package com.example.sociolla.data.remote.sources

import com.mingle.chatapp.data.remote.sources.VerticleDataModel

class ZippeDataModel(val verticalDataModel: VerticleDataModel, val horizontalDataModel : HorizontalDataModel) {

    private var horizontalModel : HorizontalDataModel
    private var verticalModel: VerticleDataModel

    init {
        this.horizontalModel = horizontalDataModel
        this.verticalModel = verticalDataModel
    }


}
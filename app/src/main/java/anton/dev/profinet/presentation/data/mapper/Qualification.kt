package anton.dev.profinet.presentation.data.mapper

import anton.dev.profinet.presentation.data.model.QualificationParamsNet
import anton.dev.profinet.presentation.domain.models.Qualification
import anton.dev.profinet.presentation.domain.models.QualificationParams

val Qualification.asString: String get() = name

val String.asQualification: Qualification get() = Qualification.valueOf(this)

val QualificationParams.asNet: QualificationParamsNet
    get() = QualificationParamsNet(
        qualification = qualification.asString,
        level = level,
        carierStart = carierStart.asString
)

val QualificationParamsNet.fromNet: QualificationParams
    get() = QualificationParams(
        qualification = qualification.asQualification,
        level = level!!,
        carierStart = carierStart.asDate!!
    )
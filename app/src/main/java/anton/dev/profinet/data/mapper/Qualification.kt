package anton.dev.profinet.data.mapper

import anton.dev.profinet.data.model.QualificationParamsNet
import anton.dev.profinet.domain.models.Qualification
import anton.dev.profinet.domain.models.QualificationParams

val Qualification.asString: String get() = name

val String.asQualification: Qualification get() = Qualification.valueOf(this)

val QualificationParams.asNet: QualificationParamsNet
    get() = QualificationParamsNet(
        qualification = qualification.asString,
        level = level,
        experience = experience
)

val QualificationParamsNet.fromNet: QualificationParams
    get() = QualificationParams(
        qualification = qualification.asQualification,
        level = level!!,
        experience = experience!!
    )
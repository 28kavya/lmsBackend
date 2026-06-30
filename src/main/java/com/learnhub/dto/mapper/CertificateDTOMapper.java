package com.learnhub.dto.mapper;

import com.learnhub.dto.CertificateDTO;
import com.learnhub.entity.Certificate;

public class CertificateDTOMapper {

    public static CertificateDTO
    mapToCertificateDTO(Certificate certificate){

        return new CertificateDTO(

                certificate.getId(),

                certificate.getCertificateNumber(),

                certificate.getIssuedDate(),

                certificate.getStudent().getId(),

                certificate.getStudent().getName(),

                certificate.getCourse().getId(),

                certificate.getCourse().getTitle()
        );
    }
}

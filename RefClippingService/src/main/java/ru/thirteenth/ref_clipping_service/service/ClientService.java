package ru.thirteenth.ref_clipping_service.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.thirteenth.ref_clipping_service.entity.dao.DefaultUri;
import ru.thirteenth.ref_clipping_service.service.impl.ClippingRefServiceImpl;
import ru.thirteenth.ref_clipping_service.service.impl.DefaultRefServiceImpl;
import ru.thirteenth.ref_clipping_service.service.impl.GeneratorServiceImpl;

@Service
public class ClientService implements GetClippingRefService, GetDefRefByClipRef {
    private DefaultRefServiceImpl defRepository;
    private ClippingRefServiceImpl clipRepository;

    @Autowired
    public ClientService(DefaultRefServiceImpl defRepository,
                         ClippingRefServiceImpl clipRepository) 
    {
        this.defRepository = defRepository;
        this.clipRepository = clipRepository;
    }

    @SneakyThrows
    @Override
    public String getClippingRef(DefaultUri uri) {
        while (true) {    /* <--------Check whether the short link has been added to the database or not*/
            int counter = 0;
            if (defRepository.existsByClientToken(uri.getClientToken().toString())) {
                break;
            }
            counter++;
            if (counter >=10) throw new Exception("Request timeout exceeded, try again later");
            Thread.sleep(1000);
        }
        return defRepository.getDefaultRefByToken(uri.getClientToken().toString())
                .getClippingRef()
                .getUrl();
    }

    @Override
    public String getDefByClip(String defUrl) {
        return null;
    }
}

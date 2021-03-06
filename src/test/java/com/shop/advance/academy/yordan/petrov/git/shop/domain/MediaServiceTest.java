package com.shop.advance.academy.yordan.petrov.git.shop.domain;

import com.shop.advance.academy.yordan.petrov.git.shop.data.dao.MediaDao;
import com.shop.advance.academy.yordan.petrov.git.shop.data.models.Media;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.dto.MediaServiceViewModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.services.MediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Class test  for .
 *
 * @author Yordan Petrov
 * @version 1.0.0.0
 * @since Jul 8, 2020.
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MediaServiceTest {

    @MockBean
    MediaDao mediaDao;

    @Autowired
    MediaService mediaService;

    @Autowired
    ModelMapper modelMapper;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMediaServiceReturnsAllMedias() {
        List<Media> media = new ArrayList<>();
        media.add(new Media());
        media.add(new Media());
        media.add(new Media());

        Mockito.when(mediaDao.findAll()).thenReturn(media);
        List<MediaServiceViewModel> mediasFetchedFromRepo = mediaService.getAllMedias();

        assertEquals(3, mediasFetchedFromRepo.size());
    }


    @Test
    public void testMediaServiceGetMediaById() {
        Media media = new Media();
        media.setId(15L);

        Mockito.when(mediaDao.findById(15L))
                .thenReturn(java.util.Optional.of(media));
        MediaServiceViewModel mediaServiceViewModel = this.modelMapper.map(media, MediaServiceViewModel.class);

        assertEquals(mediaServiceViewModel, mediaService.getMediaById(15L));
    }

    //TODO ADD TEST IF CREATES

    //TODO ADD TEST IF REMOVES

    //TODO ADD TEST IF UPDATES
}

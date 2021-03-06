package com.shop.advance.academy.yordan.petrov.git.shop.domain;

import com.shop.advance.academy.yordan.petrov.git.shop.data.dao.OpinionDao;
import com.shop.advance.academy.yordan.petrov.git.shop.data.models.Opinion;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.dto.OpinionServiceViewModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.services.OpinionService;
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
public class OpinionServiceTest {

    @MockBean
    OpinionDao opinionDao;

    @Autowired
    OpinionService opinionService;

    @Autowired
    ModelMapper modelMapper;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOpinionServiceReturnsAllOpinions() {
        List<Opinion> opinions = new ArrayList<>();
        opinions.add(new Opinion());
        opinions.add(new Opinion());
        opinions.add(new Opinion());

        Mockito.when(opinionDao.findAll()).thenReturn(opinions);
        List<OpinionServiceViewModel> opinionsFetchedFromRepo = opinionService.getAllOpinions();

        assertEquals(3, opinionsFetchedFromRepo.size());
    }


    @Test
    public void testOpinionServiceGetOpinionById() {
        Opinion opinion = new Opinion();
        opinion.setId(15L);

        Mockito.when(opinionDao.findById(15L))
                .thenReturn(java.util.Optional.of(opinion));
        OpinionServiceViewModel opinionServiceViewModel = this.modelMapper.map(opinion, OpinionServiceViewModel.class);

        assertEquals(opinionServiceViewModel, opinionService.getOpinionById(15L));
    }

    //TODO ADD TEST IF CREATES

    //TODO ADD TEST IF REMOVES

    //TODO ADD TEST IF UPDATES
}

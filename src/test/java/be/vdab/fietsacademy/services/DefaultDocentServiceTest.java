package be.vdab.fietsacademy.services;

import be.vdab.fietsacademy.domain.Adres;
import be.vdab.fietsacademy.domain.Campus;
import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.domain.Geslacht;
import be.vdab.fietsacademy.exceptions.DocentNotFound;
import be.vdab.fietsacademy.repositories.DocentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultDocentServiceTest {

    private DefaultDocentService docentService;


    @Mock
    private DocentRepository docentRepository;
    private Docent docent;

    @Before
    public void before(){
        Campus campus = new Campus("test", new Adres("test","test","test","test"));
        docent = new Docent("test", "test", BigDecimal.valueOf(100),
                "test@fiestacademy", Geslacht.MAN, campus); //
        when(docentRepository.findById(1)).thenReturn(Optional.of(docent));
        when(docentRepository.findById(-1)).thenReturn(Optional.empty());

        docentService = new DefaultDocentService(docentRepository);

    }

    @Test
    public void raise(){
        docentService.opslag(1, BigDecimal.TEN);
        assertThat(docent.getWedde()).isEqualByComparingTo("110");
        verify(docentRepository).findById(1);
    }

    @Test
    public void raiseForNonExistingDocent(){
        assertThatExceptionOfType(DocentNotFound.class)
                .isThrownBy(() -> docentService.opslag(-1, BigDecimal.TEN));
        verify(docentRepository).findById(-1);
    }


    }


package tn.esprit.devops_project.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Stock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
class OperatorServiceImplTest {

@Autowired
private OperatorServiceImpl operatorService;

    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void retrieveAllOperators() {
        final List<Operator> allOperators = this.operatorService.retrieveAllOperators();

        assertEquals(1,allOperators.size());

    }

    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void addOperator() {
        Operator operator = new Operator();
        operator.setFname("hamaa");
        operator.setLname("rouahi");
        operator.setPassword("hama");
        operatorService.addOperator(operator);

        final List<Operator> allOperators = this.operatorService.retrieveAllOperators();
        assertEquals(2, allOperators.size());

        final Operator operateur = this.operatorService.retrieveOperator(2L);
        assertEquals("hamaa", operateur.getFname());
    }

    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void deleteOperator() {
        final Operator operateur = this.operatorService.retrieveOperator(1L);
        operatorService.deleteOperator(operateur.getIdOperateur());
    }



    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void retrieveOperator() {
        final Operator stock = this.operatorService.retrieveOperator(1L);
        assertEquals("azza", stock.getFname());
    }

    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void retrieveOperator_nullId() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            final Operator operator = this.operatorService.retrieveOperator(100L);
        });
    }

    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void updateOperator() {
        Operator existingOperator = this.operatorService.retrieveOperator(1L);
        existingOperator.setFname("UpdatedFirstName");
        existingOperator.setLname("UpdatedLastName");
        this.operatorService.updateOperator(existingOperator);
        Operator updatedOperator = this.operatorService.retrieveOperator(1L);
        assertEquals("UpdatedFirstName", updatedOperator.getFname());
        assertEquals("UpdatedLastName", updatedOperator.getLname());

    }

}
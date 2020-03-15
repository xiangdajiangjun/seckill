package eazyT;

import com.seckill.purchase.dao.AccountDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class testJpa {
    @Autowired
    private MockMvc mockMvc;
    @Resource
    private AccountDao accountDao;
    @Test
    public void testAccount(){
        //Account account = accountRepository.findAccountByUsername("xiang");
        System.out.println(accountDao.getClass());
    }
}

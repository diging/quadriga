package edu.asu.spring.quadriga.validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import edu.asu.spring.quadriga.domain.settings.IAboutText;
import edu.asu.spring.quadriga.domain.settings.impl.AboutText;
import junit.framework.Assert;

/**
 * @author Nischal Samji
 * 
 *         Unit Test for Validator for storing About Text Objects.
 *
 */
public class AboutTextValidatorTest {

    @InjectMocks
    private AboutTextValidator abtTxtValidator;

    private IAboutText properabtText;
    private IAboutText improperabtText;
    private IAboutText emptyabtText;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        properabtText = new AboutText();
        properabtText.setId("TESTID");
        properabtText.setProjectId("PROJtest");
        properabtText.setDescription("<p>hello</p>");
        properabtText.setTitle("TestTitle");

        improperabtText = new AboutText();
        improperabtText.setId("TESTID");
        improperabtText.setProjectId("PROJtest");
        improperabtText.setDescription("<script>hello</script>");
        improperabtText.setTitle("<script>hello</script>");

        emptyabtText = new AboutText();
        emptyabtText.setId("TESTID");
        emptyabtText.setProjectId("PROJtest");
        emptyabtText.setDescription("");
        emptyabtText.setTitle("");

    }

    @Test
    public void test_validate_properAboutText() {
        Errors errors = new BindException(properabtText, "properabtText");
        ValidationUtils.invokeValidator(abtTxtValidator, properabtText, errors);
        Assert.assertFalse(errors.hasErrors());
    }

    @Test
    public void test_validate_improperAboutText() {
        Errors errors = new BindException(improperabtText, "improperabtText");
        ValidationUtils.invokeValidator(abtTxtValidator, improperabtText, errors);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertEquals(errors.getFieldError("description").getCode(), "about_description.proper");
        Assert.assertEquals(errors.getFieldError("title").getCode(), "about_title.proper");

    }

    @Test
    public void test_validate_emptyAboutText() {
        Errors errors = new BindException(emptyabtText, "emptyabtText");
        ValidationUtils.invokeValidator(abtTxtValidator, emptyabtText, errors);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertEquals(errors.getFieldError("title").getCode(), "about_title.required");
        Assert.assertEquals(errors.getFieldError("description").getCode(), "about_description.required");
    }
}
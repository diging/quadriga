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
    private HTMLContentValidator abtTxtValidator;

    private IAboutText properabtText;
    private IAboutText improperabtText;
    private IAboutText emptyabtText;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        properabtText = new AboutText();
        properabtText.setId("TESTID");
        properabtText.setProjectId("PROJtest");
        improperabtText.setDescription("<p>hello<img src="
                + "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAIAAACRXR/mAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNSBNYWNpbnRvc2giIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6RDUxRjY0ODgyQTkxMTFFMjk0RkU5NjI5MEVDQTI2QzUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6RDUxRjY0ODkyQTkxMTFFMjk0RkU5NjI5MEVDQTI2QzUiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpENTFGNjQ4NjJBOTExMUUyOTRGRTk2MjkwRUNBMjZDNSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpENTFGNjQ4NzJBOTExMUUyOTRGRTk2MjkwRUNBMjZDNSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PuT868wAAABESURBVHja7M4xEQAwDAOxuPw5uwi6ZeigB/CntJ2lkmytznwZFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYW1qsrwABYuwNkimqm3gAAAABJRU5ErkJggg=="
                + "</p>");
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
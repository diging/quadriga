package edu.asu.spring.quadriga.domain.dictionary;

/**
 * Interface to implement WordPower class.
 * 
 * @author : Lohith Dwaraka
 * 
 */
public interface IWordPower {

	public abstract String getId();

	public abstract String getLemma();

	public abstract String getPos();

	public abstract String getDescription();

	public abstract String getVocabulary();

	public abstract void setId(String id);

	public abstract void setLemma(String lemma);

	public abstract void setPos(String pos);

	public abstract void setDescription(String description);

	public abstract void setVocabulary(String vocabulary);

}

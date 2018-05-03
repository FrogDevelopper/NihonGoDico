package fr.frogdevelopment.nihongo.dico.search;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SearchDetails implements Serializable {

    private static final long serialVersionUID = -9208933441095607147L;

    public int seq;
    public Result kanji;
    public Result kana;
    public String romaji;
    public Set<String> pos;
    public Set<String> field;
    public Set<String> misc;
    public String info;
    public Set<String> dial;
    public Set<String> gloss = new HashSet<>();
    public Set<Sentence> sentences = new HashSet<>();


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("kanji", kanji)
                .append("kana", kana)
                .append("pos", pos)
                .append("field", field)
                .append("misc", misc)
                .append("info", info)
                .append("dial", dial)
                .append("gloss", gloss)
                .append("sentences", sentences)
                .toString();
    }

}
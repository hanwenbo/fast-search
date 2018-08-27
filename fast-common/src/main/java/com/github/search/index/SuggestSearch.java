package com.github.search.index;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.suggest.SortBy;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-11-07
 * Time: 14:59
 */
public class SuggestSearch {

    private static final String TERM_SUGGEST = "term_suggest";

    /**
     * 单个短语纠错
     * @param client
     * @param _index
     * @param _type
     * @param field
     * @param text
     * @param analyzer
     * @return
     */
    public static List<String> findByTermText(TransportClient client, String _index , String _type,String field ,String text , String analyzer){
        List<String> ll = new ArrayList<String>();
        SuggestionBuilder suggestionBuilder =
                new TermSuggestionBuilder(field)
                        .text(text)
                        .suggestMode(TermSuggestionBuilder.SuggestMode.MISSING)
                        .size(10)
                        .prefixLength(1)
                        .maxEdits(2)
                        .suggestMode(TermSuggestionBuilder.SuggestMode.MISSING)
                        .analyzer(analyzer)
                        .sort(SortBy.SCORE);

        SuggestBuilder builder = new SuggestBuilder().addSuggestion(TERM_SUGGEST,suggestionBuilder);
        SearchResponse resp = client.prepareSearch(_index).setTypes(_type).suggest(builder).get();
        Suggest suggest = resp.getSuggest();
        TermSuggestion term = suggest.getSuggestion(TERM_SUGGEST);
        List<TermSuggestion.Entry> entries = term.getEntries();
        for (TermSuggestion.Entry entry:entries) {
            List<TermSuggestion.Entry.Option> options = entry.getOptions();
            for (TermSuggestion.Entry.Option option:options) {
                ll.add(option.getText().toString());
            }
        }
        return ll;
    }


    public static List<String> findByPhraseText(){
        List<String> list = new ArrayList<String>();

        return list;
    }



}

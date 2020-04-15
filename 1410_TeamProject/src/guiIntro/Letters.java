package guiIntro;

import java.util.HashMap;
import java.util.Map;
/**
 * Class Letters.java
 * @author Khoi Nguyen
 *
 */
public class Letters {
	private static final String TAG = "repositorie.Letters";

    private Map<Character, String> letters;

    public Letters() {
        letters = new HashMap<>();

        letters.put('a', "Resources/letters/a.png");
        letters.put('b', "Resources/letters/b.png");
        letters.put('c', "Resources/letters/c.png");
        letters.put('d', "Resources/letters/d.png");
        letters.put('e', "Resources/letters/e.png");
        letters.put('f', "Resources/letters/f.png");
        letters.put('g', "Resources/letters/g.png");
        letters.put('h', "Resources/letters/h.png");
        letters.put('i', "Resources/letters/i.png");
        letters.put('j', "Resources/letters/j.png");
        letters.put('k', "Resources/letters/k.png");
        letters.put('l', "Resources/letters/l.png");
        letters.put('m', "Resources/letters/m.png");
        letters.put('n', "Resources/letters/n.png");
        letters.put('o', "Resources/letters/o.png");
        letters.put('p', "Resources/letters/p.png");
        letters.put('q', "Resources/letters/q.png");
        letters.put('r', "Resources/letters/r.png");
        letters.put('s', "Resources/letters/s.png");
        letters.put('t', "Resources/letters/t.png");
        letters.put('u', "Resources/letters/u.png");
        letters.put('v', "Resources/letters/v.png");
        letters.put('w', "Resources/letters/w.png");
        letters.put('x', "Resources/letters/x.png");
        letters.put('y', "Resources/letters/y.png");
        letters.put('z', "Resources/letters/z.png");
    }

    public String getLetterByValue(Character c) {
        return letters.get(c);
    }
}

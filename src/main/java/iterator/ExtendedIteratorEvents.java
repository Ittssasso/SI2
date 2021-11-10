package iterator;

import java.util.List;

import domain.Event;

public class ExtendedIteratorEvents implements ExtendedIterator<Event> {

	List<Event> evs;
	int position;

	public ExtendedIteratorEvents(List<Event> evs) {
		this.evs = evs;
		position = evs.size() - 1; // Last position
	}

	// uneko elementua itzultzen du eta aurrekora pasatzen da
	public Event previous() {
		Event e = evs.get(position);
		position--;
		return e;
	}

	// true aurreko elementua existitzen bada.
	public boolean hasPrevious() {
		if (position >= 0)
			return true;
		else
			return false;
	}

	// Lehendabiziko elementuan kokatzen da.
	public void goFirst() {
		position = 0;
	}

	// Azkeneko elementuan kokatzen da.
	public void goLast() {
		position = evs.size() - 1;
	}
	
	public void empty() {
		position=-1;
	}

	@Override
	public boolean hasNext() {
		return (position < evs.size());
	}

	@Override
	public Event next() {
		Event e = evs.get(position);
		position++;
		return e;
	}

}

import GraphViz.GraphDrawer;

public class StateMachine {

	UI ui;

	int table[][];
	int coloumn;// number of states

	int initialState;
	boolean finalStates[];

	String drawableAns;
	int name;

	public StateMachine(int name, UI ui, int coloumn, int table[][], int initialState, boolean finalStates[]) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.ui = ui;

		this.table = table;
		this.initialState = initialState;
		this.finalStates = finalStates;

		this.coloumn = coloumn;

		for (int i = 0; i < coloumn; i++) {
			table[i][45] = -1;
		}
	}

	public void show() {

		showAdjacent();
		makePicture();
	}

	public boolean isValid(String inp) {

		String tmp[] = inp.split("");
		int currentState = initialState;

		for (String s : tmp) {

			int index = UI.index(s);
			currentState = table[currentState][index];

			if (currentState == -1) {
				return false;
			}
		}

		return finalStates[currentState];

	}

	public void removeLoop() {

		int seenStates[] = new int[coloumn];
		// boolean secondSeenSatates[] = new boolean[coloumn];

		for (int i = 0; i < coloumn; i++) {
			seenStates[i] = 0;
			// secondSeenSatates[i] = false;
		}

		for (int k = 0; k < coloumn; k++) {

			int currentState;
			currentState = k;

//			if (seenStates[k] == 2) {// azash biroon raftim
//				continue;
//			}

			for (int i = 0; i < coloumn; i++) {

				seenStates[i] = 0;
			}
			Stack s = new Stack(1000);

			while (true) {

				seenStates[currentState] = 2;

				// boolean end = true;
				for (int i = 0; i < 128; i++) {

					if (table[currentState][i] > -1) {

						if (table[currentState][i] == k) {

							table[currentState][i] = -1;
						}

						else if (seenStates[table[currentState][i]] == 0) {
							s.push(table[currentState][i]);
						}
					}

				}

				if (s.isEmpty()) {
					break;
				}

				// lastState = currentState;
				currentState = s.pop();

			}

		}
	}

	public boolean hasLoop() {

		int seenStates[] = new int[coloumn];
		// boolean secondSeenSatates[] = new boolean[coloumn];

		for (int i = 0; i < coloumn; i++) {
			seenStates[i] = 0;
			// secondSeenSatates[i] = false;
		}

		for (int k = 0; k < coloumn; k++) {

			int currentState;
			currentState = k;

			if (seenStates[currentState] == 2) {

				continue;
			}

			//System.out.println(k + " is chosenn");
			for (int i = 0; i < coloumn; i++) {
				seenStates[i] = 0;
			}

			Stack s = new Stack(1000);

			while (true) {

				seenStates[currentState] = 2;

				// boolean end = true;
				for (int i = 0; i < 128; i++) {

					if (table[currentState][i] > -1) {

						if (table[currentState][i] == k) {
							return true;
						}

						else if (seenStates[table[currentState][i]] == 0) {
							s.push(table[currentState][i]);
						}

					}

				}

				if (s.isEmpty()) {
					break;
				}

				// lastState = currentState;
				currentState = s.pop();

			}

		}
		return false;
	}

	public void showAdjacent() {
		drawableAns = null;
		ui.textArea.append("machine " + name + " :\n");
		for (int i = 0; i < coloumn; i++) {

			ui.textArea.append("state " + i + ": ");

			for (int j = 0; j < 128; j++) {

				if (j == 45) {
					continue;
				}

				if (table[i][j] > -1) {

					ui.textArea.append("(" + table[i][j] + "," + (char) j + ") ");

					if (drawableAns == null) {
						drawableAns = i + "->" + table[i][j] + " [ label =" + (char) j + " ]";

					} else {
						drawableAns = drawableAns + i + "->" + table[i][j] + " [ label =" + (char) j + " ]";
					}
				}
			}

			ui.textArea.append("\n");
		}
		ui.textArea.append("\n");

	}

	private void makePicture() {
		GraphDrawer pic = new GraphDrawer();
		pic.draw("graph" + name, drawableAns);
	}
}
# Analisador-Descendente

{
  "terminal": ["+", "*", "(", ")", "id", "$"],
  "nonterminal": ["E", "X", "T", "Y", "F"],
  "grammar": {
    "E": ["TX"],
    "X": ["+TX", "null"],
    "T": ["FY"],
    "Y": ["*FY", "null"],
    "F": ["(E)", "id"]
  },
  "first": {
    "E": ["(", "id"],
    "X": ["+", "null"],
    "T": ["(", "id"],
    "Y": ["*", "null"],
    "F": ["(", "id"]
  },
  "follow": {
    "E": [")", "$"],
    "X": [")", "$"],
    "T": ["+", ")", "$"],
    "Y": ["+", ")", "$"],
    "F": ["*", "+", ")", "$"]
  },
  "tableM": {
    "E": {
      "id": "TX",
      "(": "TX"
    },
    "X": {
      "+": "+TX",
      ")": "null",
      "$": "null"
    },
    "T": {
      "id": "FY",
      "(": "FY"
    },
    "Y": {
      "+": "null",
      "*": "*FY",
      ")": "null",
      "$": "null"
    },
    "F": {
      "id": "id",
      "(": "(E)"
    }
  }
}

-------------------

id+id*id$

E -> XT -> XYF -> XYF -> XYid = XY

+id*id$

XY -> Xnull -> X -> XT+ = XT

id*id$

XT -> XYF -> XYid = XY

*id$

XY -> XYF* = XYF

id$

XYF -> XYid = XY

$

XY -> Xnull -> X


 [E], [X,T], [X,Y,F], [X,Y,id], [X,Y],
 [X,null], [X], [X, T, +], [X, T], [X, Y, F],
 [X, Y, id], [X, Y], [X, Y, F, *], [X, Y, F],
 [X, Y, id], [X, Y], [X, null], [X], [null], []

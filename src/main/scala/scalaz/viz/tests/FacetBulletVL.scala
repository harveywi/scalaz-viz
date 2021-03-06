package scalaz.viz.tests

import argonaut.Argonaut._
import argonaut._
import scalaz.viz.interpreter.JsonDecoder
import testz._

object FacetBulletVL {

  /**
    * Comparing content of encoded json with parsed json.
    */
  def test: () => Result = () => {
    import scalaz.viz.vegalite.grammar.Spec

    val spec =
      """
        |{
        |  "$schema": "https://vega.github.io/schema/vega-lite/v2.json",
        |  "description": "A simple bar chart with embedded data.",
        |  "data": {
        |    "values": [
        |      {"title":"Revenue","subtitle":"US$, in thousands","ranges":[150,225,300],"measures":[220,270],"markers":[250]},
        |      {"title":"Profit","subtitle":"%","ranges":[20,25,30],"measures":[21,23],"markers":[26]},
        |      {"title":"Order Size","subtitle":"US$, average","ranges":[350,500,600],"measures":[100,320],"markers":[550]},
        |      {"title":"New Customers","subtitle":"count","ranges":[1400,2000,2500],"measures":[1000,1650],"markers":[2100]},
        |      {"title":"Satisfaction","subtitle":"out of 5","ranges":[3.5,4.25,5],"measures":[3.2,4.7],"markers":[4.4]}
        |    ]
        |  },
        |  "facet": {
        |    "row": {
        |      "field": "title", "type": "ordinal",
        |      "header": {"labelAngle": 0, "title": ""}
        |    }
        |  },
        |  "spec": {
        |    "layer": [{
        |      "mark": {"type": "bar", "color": "#eee"},
        |      "encoding": {
        |        "x": {
        |          "field": "ranges[2]", "type": "quantitative", "scale": {"nice": false},
        |          "title": null
        |        }
        |      }
        |    },{
        |      "mark": {"type": "bar", "color": "#ddd"},
        |      "encoding": {
        |        "x": {"field": "ranges[1]", "type": "quantitative"}
        |      }
        |    },{
        |      "mark": {"type": "bar", "color": "#ccc"},
        |      "encoding": {
        |        "x": {"field": "ranges[0]", "type": "quantitative"}
        |      }
        |    },{
        |      "mark": {"type": "bar", "color": "lightsteelblue", "size": 10},
        |      "encoding": {
        |        "x": {"field": "measures[1]", "type": "quantitative"}
        |      }
        |    },{
        |      "mark": {"type": "bar", "color": "steelblue", "size": 10},
        |      "encoding": {
        |        "x": {"field": "measures[0]", "type": "quantitative"}
        |      }
        |    },{
        |      "mark": {"type": "tick", "color": "black"},
        |      "encoding": {
        |        "x": {"field": "markers[0]", "type": "quantitative"}
        |      }
        |    }]
        |  },
        |  "resolve": {
        |    "scale": {
        |      "x": "independent"
        |    }
        |  },
        |  "config": {
        |    "tick": {"thickness": 2}
        |  }
        |}
      """.stripMargin

    val json = spec.parseOption.get // TODO obviously naughty

    val decoded: DecodeResult[Spec] = JsonDecoder.getDecoder(Spec.schema).decodeJson(json)

    compareWDecoded(this.getClass.getSimpleName, json, decoded)
  }

}
